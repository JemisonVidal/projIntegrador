package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantProfileRepository extends JpaRepository<ApplicantProfile, Long> {

}
