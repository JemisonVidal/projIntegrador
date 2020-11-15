package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.profile.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends BaseRepository<Opportunity, Long> {

    Page<Opportunity> findAllByCompany(Profile company, Pageable pageable);

    List<Opportunity> findByAppliedUsers_Id(Long appliedUsers_id);

    Boolean existsByIdAndAppliedUsers_Id(Long id, Long appliedUsers_id);

}
