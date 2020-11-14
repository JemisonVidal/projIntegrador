package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.profile.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

}
