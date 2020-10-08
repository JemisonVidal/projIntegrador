package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.model.TypeEnum;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.UserDTO;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.security.JWTAuthenticationEntryPoint;
import br.com.house.digital.projetointegrador.security.JWTRequestFilter;
import br.com.house.digital.projetointegrador.security.JWTTokenUtil;
import br.com.house.digital.projetointegrador.security.WebSecurityConfig;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})                                 // Tell that our current configuration file is "application-test.properties"
@WebMvcTest(controllers = AuthenticationController.class) // This annotation configures our class to test a controller
@AutoConfigureMockMvc                                     // This annotation will configure an object where we can simulate requests
@ContextConfiguration(classes = {JWTAuthenticationEntryPoint.class,
        JWTRequestFilter.class,
        JWTTokenUtil.class,
        WebSecurityConfig.class,
        UserDetailsServiceImpl.class,
        UserRepository.class
})
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthenticationService authenticationService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should register a user on database and return status 201")
    public void testRegisterUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .email("natasha.romanov@gmail.com")
                .name("Natasha Romanov")
                .password("BlackWidow2020")
                .type(TypeEnum.USER)
                .build();

        String json = objectMapper.writeValueAsString(userDTO);

        when(authenticationService.save(any())).thenReturn(User.builder()
                .uuid(UUID.randomUUID())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .build());

        RequestBuilder request = post("/v1/api/register")
                .header("Content-type", "application/json")
                .header("Accept", "application/json")
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(userDTO.getName()))
                .andExpect(jsonPath("email").value(userDTO.getEmail()));
    }

}
