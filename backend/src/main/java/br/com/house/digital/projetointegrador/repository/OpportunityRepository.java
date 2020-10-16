package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
}