package br.com.house.digital.projetointegrador.security;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JWTUtilTest {

    JWTUtil jwtUtil = new JWTUtil();

    String name = "Tony Stark";
    String email = "tony@starkindustries.com";
    User user = User.builder()
        .type(UserType.APPLICANT)
        .profile(ApplicantProfile.builder()
            .id(1L)
            .name(name).build())
        .email(email).build();
    String token;

    @Test
    @Order(1)
    void generateTokenTest() {
        String token = jwtUtil.generateToken(user);
        assertThat(token).isNotNull();
        Arrays.stream(token.split("\\.")).limit(2).forEach(x -> assertThat(x).isBase64());
        this.token = token;
    }

    @Test
    @Order(2)
    void getEmailFromTokenTest() {
        assertThat(jwtUtil.getEmailFromToken(token)).isEqualTo(user.getEmail());
    }

    @Test
    @Order(3)
    void getExpirationDateFromTokenTest() {
        assertThat(jwtUtil.getExpirationDateFromToken(token)).isAfter(new Date(System.currentTimeMillis()));
    }

    @Test
    @Order(4)
    void getProfileIdFromTokenTest() {
        assertThat(jwtUtil.getProfileIdFromToken(token)).isEqualTo(user.getProfile().getId());
    }

    @Test
    @Order(5)
    void validateTokenTest() {
        assertThat(jwtUtil.validateToken(token, user)).isTrue();
    }

    @Test
    @Order(6)
    void invalidTokenSignatureTest() {
        Throwable exception = catchThrowable(() -> jwtUtil.validateToken(token.concat("a"), user));
        assertThat(exception).isInstanceOf(SignatureException.class);
    }

    @Test
    @Order(7)
    void invalidUserTokenClaimsTest() {
        User user = User.builder().type(UserType.COMPANY).email("bruce@waynecorp.com").build();
        assertThat(jwtUtil.validateToken(token, user)).isFalse();
    }
}