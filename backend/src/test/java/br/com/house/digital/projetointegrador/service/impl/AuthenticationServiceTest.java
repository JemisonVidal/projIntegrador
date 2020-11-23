package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.dto.authentication.LoginDTO;
import br.com.house.digital.projetointegrador.dto.authentication.RegisterDTO;
import br.com.house.digital.projetointegrador.dto.authentication.TokenDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import br.com.house.digital.projetointegrador.service.exceptions.DataIntegrityException;
import br.com.house.digital.projetointegrador.service.exceptions.EmailExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JWTUtil.class, UserDetailsServiceImpl.class, ModelMapper.class})
@ActiveProfiles({"Test"})
public class AuthenticationServiceTest {

    AuthenticationService authenticationService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationServiceImpl(userRepository,
            authenticationManager,
            passwordEncoder,
            jwtUtil);
    }

    @ParameterizedTest
    @EnumSource(UserType.class)
    @DisplayName("Should register a user on database")
    public void registerUserTest(UserType type) {

        RegisterDTO user = RegisterDTO.builder()
            .name("Natasha Romanov")
            .email("natasha_romanov@shield.com")
            .password("Shield2020")
            .type(type)
            .build();

        given(userRepository.existsByEmail(user.getEmail())).willReturn(false);

        given(userRepository.save(any(User.class))).willReturn(User.builder()
            .id(1L)
            .email(user.getEmail())
            .password(UUID.randomUUID().toString())
            .type(user.getType())
            .build());

        User entity = authenticationService.save(user);

        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getEmail()).isEqualTo(user.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should throw an exception on register with null user type")
    public void registerUserNullTypeTest() {

        RegisterDTO user = RegisterDTO.builder()
            .name("Natasha Romanov")
            .email("natasha_romanov@shield.com")
            .password("Shield2020")
            .type(null)
            .build();

        given(userRepository.existsByEmail(user.getEmail())).willReturn(false);

        Throwable exception = catchThrowable(() -> authenticationService.save(user));

        assertThat(exception).isInstanceOf(DataIntegrityException.class);

        verify(userRepository, never()).save(any(User.class));
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should throw an exception when trying to register with existing email")
    public void registerExistentUserTest() {

        RegisterDTO user = RegisterDTO.builder()
            .name("Natasha Romanov")
            .email("natasha_romanov@shield.com")
            .password("Shield2020")
            .type(UserType.APPLICANT)
            .build();

        given(userRepository.existsByEmail(user.getEmail())).willReturn(true);

        Throwable exception = catchThrowable(() -> authenticationService.save(user));

        assertThat(exception).isInstanceOf(EmailExistsException.class)
            .hasMessage("Email: natasha_romanov@shield.com already exists in the database.");

        verify(userRepository, never()).save(any(User.class));
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should authenticate a user when given valid credentials")
    public void authenticateUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO("natasha_romanov@shield.com", "Shield2020");
        Authentication authentication = mock(Authentication.class);

        User user = User.builder()
            .id(2L)
            .email("natasha_romanov@shield.com")
            .password("Shield2020")
            .type(UserType.APPLICANT)
            .profile(ApplicantProfile.builder().id(1L).build())
            .build();

        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(user);

        TokenDTO jwtResponse = authenticationService.authenticate(loginDTO);

        assertThat(jwtUtil.getEmailFromToken(jwtResponse.getToken())).isEqualTo(user.getEmail());
        assertThat(jwtUtil.getProfileIdFromToken(jwtResponse.getToken())).isEqualTo(user.getProfileId());
    }

    @Test
    @DisplayName("Should throw an exception when given invalid credentials")
    public void authenticateNonExistentUser() {
        LoginDTO loginDTO = new LoginDTO("natasha_romanov@shield.com", "Shield2020");

        given(userRepository.findByEmail(loginDTO.getEmail())).willReturn(Optional.empty());
        given(authenticationManager.authenticate(any()))
            .will(invocation -> userDetailsService.loadUserByUsername(loginDTO.getEmail()));

        Throwable exception = catchThrowable(() -> authenticationService.authenticate(loginDTO));

        assertThat(exception).isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("User not found with email: " + loginDTO.getEmail());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(loginDTO.getEmail());
    }

}
