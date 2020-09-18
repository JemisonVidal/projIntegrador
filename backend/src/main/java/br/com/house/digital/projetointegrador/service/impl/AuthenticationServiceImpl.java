package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.model.*;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.security.JWTTokenUtil;
import br.com.house.digital.projetointegrador.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public JWTResponse authenticate(LoginInfo loginInfo) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginInfo.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            final User user = userRepository.findByEmail(loginInfo.getEmail()).get();
            return new JWTResponse(user, token);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }
        return userRepository.save(user);
    }
}
