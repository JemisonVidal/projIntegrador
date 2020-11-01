package br.com.house.digital.projetointegrador.service;

import br.com.house.digital.projetointegrador.dto.authentication.LoginDTO;
import br.com.house.digital.projetointegrador.dto.authentication.TokenDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.service.exceptions.EmailExistsException;
import br.com.house.digital.projetointegrador.service.impl.AuthenticationServiceImpl;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JWTUtil.class, UserDetailsServiceImpl.class})
@ActiveProfiles({"Test"})
public class AuthenticationServiceTest {

    AuthenticationService authenticationService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationServiceImpl(userRepository,
                authenticationManager,
                passwordEncoder,
                jwtUtil);
    }

    @Test
    @DisplayName("Should register a user on database")
    public void registerUserTest() {

        User user = User.builder()
                .email("natasha_romanov@shield.com")
                .name("Natasha Romanov")
                .password("Shield2020")
                .type(UserType.APPLICANT)
                .build();

        given(userRepository.save(user)).willReturn(User.builder()
                .id(1L)
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .type(user.getType())
                .build());

        User entity = authenticationService.save(user);

        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getEmail()).isEqualTo(user.getEmail());
        assertThat(entity.getName()).isEqualTo(user.getName());
        assertThat(entity.getPassword()).isNotEqualTo(user.getPassword());

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should return null when try to register an existent user")
    public void registerExistentUserTest() {

        User user = User.builder()
                .email("natasha_romanov@shield.com")
                .name("Natasha Romanov")
                .password("Shield2020")
                .type(UserType.APPLICANT)
                .build();

        given(userRepository.existsByEmail(user.getEmail())).willReturn(true);

        Throwable exception = catchThrowable(() -> authenticationService.save(user));

        assertThat(exception).isInstanceOf(EmailExistsException.class)
                .hasMessage("Email: natasha_romanov@shield.com already exists in the database.");

        verify(userRepository, never()).save(user);
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should authenticate a user when given valid credentials")
    public void authenticateUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO("natasha_romanov@shield.com", "Shield2020");
        Authentication authentication = mock(Authentication.class);

        User user = User.builder()
                .id(2L)
                .email("natasha_romanov@shield.com")
                .name("Natasha Romanov")
                .password("Shield2020")
                .type(UserType.APPLICANT)
                .profile(ApplicantProfile.builder().id(1L).build())
                .build();

        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(user);

        TokenDTO jwtResponse = authenticationService.authenticate(loginDTO);

        assertThat(jwtUtil.getEmailFromToken(jwtResponse.getToken())).isEqualTo(user.getEmail());
        assertThat(jwtUtil.getProfileIdFromToken(jwtResponse.getToken())).isEqualTo(user.getProfileId());
    }

    @Test
    @DisplayName("Should throw an exception when given invalid credentials")
    public void authenticateNonExistentUser() {
        LoginDTO loginDTO = new LoginDTO("natasha_romanov@shield.com", "Shield2020");

        given(userRepository.findByEmail(loginDTO.getEmail())).willReturn(Optional.empty());
        given(authenticationManager.authenticate(any()))
                .will(invocation -> userDetailsService.loadUserByUsername(loginDTO.getEmail()));

        Throwable exception = catchThrowable(() -> authenticationService.authenticate(loginDTO));

        assertThat(exception).isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found with email: " + loginDTO.getEmail());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(loginDTO.getEmail());
    }

}
