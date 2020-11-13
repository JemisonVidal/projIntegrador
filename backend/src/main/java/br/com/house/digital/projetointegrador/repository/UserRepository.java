package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findByProfile(Profile profile);

}
