package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.authentication.LoginDTO;
import br.com.house.digital.projetointegrador.dto.authentication.RegisterDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthenticationControllerIntegrationTest {

    private static final String NAME = "Ada Lovelace";
    private static final String EMAIL = "ada@lovelace.com";
    private static final String PASSWORD = "Lov3Lac3";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Should register a new user and return status 201")
    void registerTest() throws Exception {
        RegisterDTO dto = RegisterDTO.builder()
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .type(UserType.APPLICANT)
            .build();

        String location = mvc.perform(post("/v1/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getHeader("Location");

        assertThat(location).isNotNull();
        assertThat(location.contains("/v1/api/profile/applicant")).isTrue();

        Optional<User> userOptional = repository.findByEmail(dto.getEmail());

        assertThat(userOptional).isNotEmpty();

        User user = userOptional.get();

        assertThat(user.getType()).isEqualTo(UserType.APPLICANT);
        assertThat(user.getPassword()).isNotEqualTo(dto.getPassword());
        assertThat(user.getProfile().getName()).isEqualTo(dto.getName());
    }

    @Test
    @DisplayName("Should not register when email exists and return status 400")
    void registerExistingEmailTest() throws Exception {
        repository.save(User.builder()
            .email(EMAIL)
            .password(passwordEncoder.encode(PASSWORD))
            .type(UserType.APPLICANT)
            .profile(ApplicantProfile.builder().name(NAME).build())
            .build());

        RegisterDTO dto = RegisterDTO.builder()
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .type(UserType.APPLICANT)
            .build();

        mvc.perform(post("/v1/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.msg").value("Email: " + EMAIL + " already exists in the database."));
    }

    @Test
    @DisplayName("Should authenticate when given valid email and password")
    void authenticationTest() throws Exception {
        repository.save(User.builder()
            .email(EMAIL)
            .password(passwordEncoder.encode(PASSWORD))
            .type(UserType.APPLICANT)
            .profile(ApplicantProfile.builder().name(NAME).build())
            .build());

        LoginDTO dto = LoginDTO.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();

        mvc.perform(post("/v1/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("Should return status 400 when email or password is incorrect")
    void invalidAuthenticationTest() throws Exception {
        repository.save(User.builder()
            .email(EMAIL)
            .password(passwordEncoder.encode(PASSWORD))
            .type(UserType.APPLICANT)
            .profile(ApplicantProfile.builder().name(NAME).build())
            .build());

        LoginDTO dto = LoginDTO.builder()
            .email(EMAIL)
            .password("wrong password")
            .build();

        mvc.perform(post("/v1/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.msg").value("Wrong email or password."));
    }

}
