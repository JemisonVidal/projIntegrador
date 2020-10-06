package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.UserDTO;
import br.com.house.digital.projetointegrador.model.JWTResponse;
import br.com.house.digital.projetointegrador.model.LoginInfo;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/api")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthenticationController(AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
		this.authenticationService = authenticationService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register")
	public ResponseEntity<Void> save(@RequestBody @Valid UserDTO userDTO) {
		final User user = authenticationService.save(userDTO.toUser(passwordEncoder));
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JWTResponse> authenticate(@RequestBody @Valid LoginInfo loginInfo) throws Exception {
		final JWTResponse jwtResponse = authenticationService.authenticate(loginInfo);
		return ResponseEntity.ok(jwtResponse);
	}

}
