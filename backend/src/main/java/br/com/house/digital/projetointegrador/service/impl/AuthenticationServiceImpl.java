package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.dto.authentication.LoginDTO;
import br.com.house.digital.projetointegrador.dto.authentication.TokenDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import br.com.house.digital.projetointegrador.service.exceptions.EmailExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public TokenDTO authenticate(LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO
            .getEmail(), loginDTO.getPassword()));
        final User user = (User) authentication.getPrincipal();
        final String token = jwtUtil.generateToken(user);
        return new TokenDTO(token);
    }

    @Override
    public User save(User user) {
        final String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new EmailExistsException("Email: " + email + " already exists in the database.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
