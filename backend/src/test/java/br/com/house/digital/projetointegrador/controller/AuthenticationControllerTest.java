package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.authentication.LoginDTO;
import br.com.house.digital.projetointegrador.dto.authentication.RegisterDTO;
import br.com.house.digital.projetointegrador.dto.authentication.TokenDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.security.JWTAuthenticationEntryPoint;
import br.com.house.digital.projetointegrador.security.JWTRequestFilter;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.security.WebSecurityConfig;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc
@Import(AuthenticationController.class)
@ContextConfiguration(classes = {
    JWTAuthenticationEntryPoint.class,
    WebSecurityConfig.class,
    JWTRequestFilter.class,
    JWTUtil.class,
    ModelMapper.class
})
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should register a user and return status 201")
    public void registerUserTest() throws Exception {
        RegisterDTO userDTO = RegisterDTO.builder()
            .email("natasha_romanov@shield.com")
            .name("Natasha Romanov")
            .password("Shield2020")
            .type(UserType.APPLICANT)
            .build();

        when(authenticationService.save(userDTO))
            .thenReturn(User.builder()
                .id(1L)
                .type(userDTO.getType())
                .profile(ApplicantProfile.builder().id(1L).build())
                .build());

        RequestBuilder request = post("/v1/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO));

        MockHttpServletResponse response = mvc.perform(request)
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse();

        verify(authenticationService, times(1)).save(userDTO);
        assertThat(response.getHeader("Location")).isEqualTo("http://localhost/v1/api/profile/applicant/1");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"notanemail.com"})
    @DisplayName("Should return error 400 when trying to register a user with invalid email")
    public void registerUserWithEmptyEmail(String email) throws Exception {
        RegisterDTO userDTO = RegisterDTO.builder()
            .email(email)
            .name("Natasha Romanov")
            .password("Shield2020")
            .type(UserType.APPLICANT)
            .build();

        RequestBuilder request = post("/v1/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO));

        mvc.perform(request)
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should return error 400 when trying to register a user with null or empty name")
    public void registerUserWithEmptyName(String name) throws Exception {
        RegisterDTO userDTO = RegisterDTO.builder()
            .email("natasha_romanov@shield.com")
            .name(name)
            .password("Shield2020")
            .type(UserType.APPLICANT)
            .build();

        RequestBuilder request = post("/v1/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO));

        mvc.perform(request)
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"<8chars", "invalidpassword"})
    @DisplayName("Should return an error 400 when try to register a user with invalid password")
    public void registerUserWithEmptyPassword(String password) throws Exception {
        RegisterDTO userDTO = RegisterDTO.builder()
            .email("natasha_romanov@shield.com")
            .name("Natasha Romanov")
            .password(password)
            .type(UserType.APPLICANT)
            .build();

        RequestBuilder request = post("/v1/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO));

        mvc.perform(request)
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    @Test
    @DisplayName("Should authenticate a user and return a token")
    public void authenticateUserTest() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
            .email("natasha_romanov@shield.com")
            .password("Shield2020")
            .build();

        String token = UUID.randomUUID().toString();

        when(authenticationService.authenticate(any(LoginDTO.class)))
            .thenReturn(new TokenDTO(token));

        RequestBuilder request = post("/v1/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO));

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("token").value(token));
    }

    @Test
    @DisplayName("Should throw an exception and return status unauthorized")
    public void authenticateUserWithWrongCredentialsTest() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
            .email("natasha_romanov@shield.com")
            .password("Shield2020")
            .build();

        when(authenticationService.authenticate(any(LoginDTO.class)))
            .thenThrow(new Exception("INVALID_CREDENTIALS", new BadCredentialsException("Access denied")));

        RequestBuilder request = post("/v1/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO));

        mvc.perform(request)
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should throw an error when try to authenticate a user that does not exists")
    public void authenticateUserWithUserNameNotFound() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
            .email("natasha_romanov@shield.com")
            .password("Shield2020")
            .build();

        when(authenticationService.authenticate(any(LoginDTO.class)))
            .thenThrow(new UsernameNotFoundException("Username not found with email " + loginDTO.getEmail()));

        RequestBuilder request = post("/v1/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO));

        mvc.perform(request)
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return status bad request")
    public void authenticateUserWithEmptyLoginRequestTest() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
            .email("")
            .password("")
            .build();

        RequestBuilder request = post("/v1/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO));

        mvc.perform(request)
            .andExpect(status().isBadRequest());
    }

}
