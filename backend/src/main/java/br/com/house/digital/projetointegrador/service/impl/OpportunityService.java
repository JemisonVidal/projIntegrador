package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.Profile;
import br.com.house.digital.projetointegrador.repository.OpportunityRepository;
import br.com.house.digital.projetointegrador.service.exceptions.UserForbiddenException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpportunityService extends BaseServiceImpl<Opportunity, Long> {

	private final OpportunityRepository opportunityRepository;

	@Autowired
	public OpportunityService(OpportunityRepository opportunityRepository,
							  ModelMapper modelMapper) {
		super(opportunityRepository, modelMapper, Opportunity.class);
		this.opportunityRepository = opportunityRepository;
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.typeMap(NewOpportunityDTO.class, Opportunity.class)
			.addMappings(mapper -> mapper.skip(Opportunity::setRequirements));
	}

	@Transactional
	public Opportunity save(NewOpportunityDTO dto, User user) {
		Opportunity opportunity = this.convertToEntity(dto);
		opportunity.setCompany(user.getProfile());
		super.replaceList(dto.getRequirements(), opportunity.getRequirements());
		return super.save(opportunity);
	}

	public void apply(Long id, User user) {
		Opportunity opportunity = this.findById(id);
		opportunity.getAppliedUsers().add(user.getProfile());
		save(opportunity);
	}

	public OpportunityDTO mapDTO(Opportunity opportunity) {
		OpportunityDTO dto = super.convertFromEntity(opportunity, OpportunityDTO.class);
		dto.setIsApplied(isApplied(opportunity.getId()));
		return dto;
	}

	public Boolean isApplied(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null &&
			authentication.getAuthorities()
				.stream()
				.anyMatch(a -> a.getAuthority().equals(UserType.APPLICANT.name()))) {
			final User user = (User) authentication.getPrincipal();
			return opportunityRepository.existsByIdAndAppliedUsers_Id(id, user.getProfileId());
		}
		return null;
	}

	public List<OpportunityDTO> findAllByCompanyId(Long id) {
    	return this.opportunityRepository.findAllByCompany_Id(id).stream()
			.map(this::mapDTO)
			.collect(Collectors.toList());
	}

	public List<OpportunityDTO> findAppliedOpportunitiesByProfileId(Long id) {
		return opportunityRepository.findByAppliedUsers_Id(id).stream()
			.map(this::mapDTO)
			.collect(Collectors.toList());
	}

	public List<ApplicantProfileDTO> findAppliedUsersByOpportunityId(Long id) {
		final Opportunity opportunity = findByIdAndCheckOwner(id);
		final List<Profile> users = opportunity.getAppliedUsers();
		return users.stream().map(u -> this.modelMapper.map(u, ApplicantProfileDTO.class)).collect(Collectors.toList());
	}

	private Opportunity findByIdAndCheckOwner(Long id) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Opportunity opportunity = findById(id);
		if (!opportunity.getCompanyId().equals(user.getProfileId())) {
			throw new UserForbiddenException("Resource access is not authorized for this user.");
		}
		return opportunity;
	}

	public void toggleActive(Long id) {
		Opportunity opportunity = this.findByIdAndCheckOwner(id);
		opportunity.setActive(!opportunity.getActive());
		this.save(opportunity);
	}

	@Transactional
	public Opportunity patch(Long id, NewOpportunityDTO partial) {
		Opportunity opportunity = this.findByIdAndCheckOwner(id);
		if (partial.getRequirements() != null) {
			replaceList(partial.getRequirements(), opportunity.getRequirements());
		}
		return super.patch(partial, opportunity);
	}

}
