package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.model.JWTResponse;
import br.com.house.digital.projetointegrador.model.LoginInfo;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.UserDTO;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> save(@RequestBody @Valid UserDTO userDTO) {
        final User user = authenticationService.save(userDTO.toUser(passwordEncoder));
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTResponse> authenticate(@RequestBody @Valid LoginInfo loginInfo) throws Exception {
        final JWTResponse jwtResponse = authenticationService.authenticate(loginInfo);
        return ResponseEntity.ok(jwtResponse);
    }

}
