package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.profile.Profile;
import br.com.house.digital.projetointegrador.repository.OpportunityRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OpportunityService extends BaseServiceImpl<Opportunity, Long> {

	private final OpportunityRepository opportunityRepository;
	private final CompanyProfileService companyProfileService;

    @Autowired
    public OpportunityService(OpportunityRepository opportunityRepository,
							  ModelMapper modelMapper,
							  CompanyProfileService companyProfileService) {
        super(opportunityRepository, modelMapper, Opportunity.class);
		this.companyProfileService = companyProfileService;
		this.opportunityRepository = opportunityRepository;
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public void apply(Long id, User user) {
		Opportunity opportunity = this.findById(id);
		opportunity.getAppliedUsers().add(user);
		save(opportunity);
	}

	public Page<Opportunity> findAllByCompanyId(Long id, Pageable pageable) {
    	Profile company = companyProfileService.findById(id);
    	return this.opportunityRepository.findAllByCompany(company, pageable);
	}

}
