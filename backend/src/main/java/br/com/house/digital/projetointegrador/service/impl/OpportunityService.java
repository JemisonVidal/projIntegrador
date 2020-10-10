package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;

    @Autowired
    public OpportunityService(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    public Opportunity create(Opportunity opportunity) {
        return opportunityRepository.save(opportunity);
    }

}
