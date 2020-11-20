package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
class UserDetailsServiceImplTest {

    UserDetailsServiceImpl userDetailsService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Should return user details instance when email exists")
    void loadUserByUsernameTest() {
        String email = "test@email.com";
        final User user = User.builder().email(email).build();
        given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(user));
        assertThat(userDetailsService.loadUserByUsername(email)).isEqualTo(user);
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    void userNotFoundTest() {
        String email = "test@email.com";
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        Throwable exception = catchThrowable(() -> userDetailsService.loadUserByUsername(email));
        assertThat(exception).isInstanceOf(UsernameNotFoundException.class);
    }
}