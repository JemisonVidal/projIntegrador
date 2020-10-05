package br.com.house.digital.projetointegrador.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})                                 // Tell that our current configuration file is "application-test.properties"
@WebMvcTest(controllers = AuthenticationController.class) // This annotation configures our class to test a controller
@AutoConfigureMockMvc                                     // This annotation will configure an object where we can simulate requests
public class AuthenticationControllerTest {

}
