package br.com.house.digital.projetointegrador.repository;

import java.util.Optional;

import br.com.house.digital.projetointegrador.model.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.house.digital.projetointegrador.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findByProfile(Profile profile);

}
