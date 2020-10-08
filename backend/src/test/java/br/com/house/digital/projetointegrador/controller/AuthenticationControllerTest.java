package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.model.TypeEnum;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.UserDTO;
import br.com.house.digital.projetointegrador.security.JWTAuthenticationEntryPoint;
import br.com.house.digital.projetointegrador.security.JWTRequestFilter;
import br.com.house.digital.projetointegrador.security.JWTTokenUtil;
import br.com.house.digital.projetointegrador.security.WebSecurityConfig;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        JWTTokenUtil.class
})
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    UserDetailsService userDetailsService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should register a user on database and return status 201.")
    public void registerUserTest() throws Exception {
        UserDTO dto = UserDTO.builder()
                .name("Natasha Romanov")
                .email("black_widow@shield.com.br")
                .password("WidowShield2020")
                .type(TypeEnum.USER)
                .build();

        User user = User.builder()
                .uuid(UUID.randomUUID())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .type(dto.getType())
                .build();

        when(authenticationService.save(any(User.class))).thenReturn(user);

        RequestBuilder request = post("/v1/api/register")
                .header("Content-type", "application/json")
                .content(objectMapper.writeValueAsString(dto));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(dto.getName()))
                .andExpect(jsonPath("email").value(dto.getEmail()))
                .andExpect(jsonPath("type").value(TypeEnum.USER.toString()));

        verify(authenticationService, times(1)).save(any(User.class));
    }

}
