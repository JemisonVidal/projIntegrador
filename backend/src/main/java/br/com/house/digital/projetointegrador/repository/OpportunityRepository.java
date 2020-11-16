package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.Opportunity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends BaseRepository<Opportunity, Long> {

    List<Opportunity> findAllByCompany_Id(Long id);

    List<Opportunity> findByAppliedUsers_Id(Long appliedUsers_id);

    Boolean existsByIdAndAppliedUsers_Id(Long id, Long appliedUsers_id);

}
