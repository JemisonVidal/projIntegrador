package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.profile.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

}
