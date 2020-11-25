package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.authentication.LoginDTO;
import br.com.house.digital.projetointegrador.dto.authentication.RegisterDTO;
import br.com.house.digital.projetointegrador.dto.authentication.TokenDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/v1/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ApiOperation(value = "Registers a new user.")
    public ResponseEntity<Void> save(@RequestBody @Valid RegisterDTO registerDTO) {
        final User user = authenticationService.save(registerDTO);
        final ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
        builder.removePathExtension();
        URI uri = builder.path("/v1/api/profile/{type}/{id}").buildAndExpand(user.getType().name().toLowerCase(), user.getProfileId())
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/authenticate")
    @ApiOperation(value = "Authenticates an user with the given email and password.")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        final TokenDTO tokenDTO = authenticationService.authenticate(loginDTO);
        return ResponseEntity.ok(tokenDTO);
    }

}
