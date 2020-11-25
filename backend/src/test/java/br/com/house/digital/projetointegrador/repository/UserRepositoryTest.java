package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles({"test"})
class UserRepositoryTest {

    User user;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = User.builder()
            .email("zaphod@galaxy.net")
            .password("Zaphod1234")
            .type(UserType.APPLICANT)
            .build();
        userRepository.save(user);
    }

    @Test
    void findByEmailTest() {
        assertThat(userRepository.findByEmail(user.getEmail())).contains(user);
    }

    @Test
    void findByEmailNotExistsTest() {
        assertThat(userRepository.findByEmail("arthur@backpackers.com")).isEmpty();
    }

    @Test
    void existsByEmailTest() {
        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    void notExistsByEmailTest() {
        assertThat(userRepository.existsByEmail("arthur@backpackers.com")).isFalse();
    }

}