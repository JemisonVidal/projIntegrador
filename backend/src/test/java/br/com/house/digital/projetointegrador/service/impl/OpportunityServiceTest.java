package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.annotation.WithMockCustomUser;
import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.Requirement;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.repository.OpportunityRepository;
import br.com.house.digital.projetointegrador.service.exceptions.UserForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
class OpportunityServiceTest {

    @MockBean
    OpportunityRepository opportunityRepository;

    ModelMapper mapper = new ModelMapper();
    OpportunityService opportunityService;
    Opportunity opportunity;
    CompanyProfile company;

    @BeforeEach
    void setUp() {
        this.opportunityService = new OpportunityService(opportunityRepository, mapper);
        given(opportunityRepository.existsByIdAndAppliedUsers_Id(anyLong(), anyLong())).willReturn(true);
        company = CompanyProfile.builder().id(1L).name("Jabba the Hut Racing Team").build();
        opportunity = Opportunity.builder()
            .id(1L)
            .name("Pod Racing Pilot")
            .company(company)
            .appliedUsers(new ArrayList<>())
            .requirements(new ArrayList<>())
            .active(true)
            .build();
    }

    @Test
    @DisplayName("Should save new opportunity")
    void saveOpportunityTest() {
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("Vaga para teste")
            .location("São Paulo, SP")
            .description("Descrição da vaga")
            .requirements(Collections.singletonList(Requirement.builder()
                .name("Requerimento")
                .knowledgeLevel(KnowledgeLevel.BASIC)
                .build()))
            .build();
        User user = User.builder()
            .profile(CompanyProfile.builder().id(1L).build())
            .build();

        given(opportunityRepository.save(any(Opportunity.class))).willAnswer(invocation -> invocation.getArguments()[0]);

        Opportunity created = opportunityService.save(dto, user);

        assertThat(created.getName()).isEqualTo(dto.getName());
        assertThat(created.getLocation()).isEqualTo(dto.getLocation());
        assertThat(created.getDescription()).isEqualTo(dto.getDescription());
        assertThat(created.getRequirements()).isEqualTo(dto.getRequirements());
        assertThat(created.getCompanyId()).isEqualTo(user.getProfileId());
        verify(opportunityRepository).save(any(Opportunity.class));
    }

    @Test
    @DisplayName("Should add the user into the opportunity applied users list")
    void applyTest() {
        given(opportunityRepository.findById(anyLong())).willReturn(Optional.of(opportunity));
        given(opportunityRepository.save(any(Opportunity.class))).willReturn(opportunity);
        opportunityService.apply(1L, User.builder().profile(new ApplicantProfile()).build());
        assertThat(opportunity.getAppliedUsers()).hasAtLeastOneElementOfType(ApplicantProfile.class);
        assertThat(opportunity.getAppliedUsers().size()).isEqualTo(1);
    }

    @ParameterizedTest
    @DisplayName("Should convert correctly into a DTO")
    @ValueSource(booleans = {true, false})
    @WithMockCustomUser
    void mapDTOTest(boolean applied) {
        given(opportunityRepository.existsByIdAndAppliedUsers_Id(anyLong(), anyLong())).willReturn(applied);
        OpportunityDTO dto = opportunityService.mapDTO(opportunity);
        assertThat(dto.getId()).isEqualTo(opportunity.getId());
        assertThat(dto.getName()).isEqualTo(opportunity.getName());
        assertThat(dto.getIsApplied()).isEqualTo(applied);
        assertThat(dto.getCompanyId()).isEqualTo(company.getId());
        assertThat(dto.getCompanyName()).isEqualTo(company.getName());
    }

    @ParameterizedTest
    @DisplayName("Should return true or false if the user is applied or not to the opportunity")
    @ValueSource(booleans = {true, false})
    @WithMockCustomUser
    void isAppliedTest(boolean applied) {
        given(opportunityRepository.existsByIdAndAppliedUsers_Id(anyLong(), anyLong())).willReturn(applied);
        assertThat(opportunityService.isApplied(1L)).isEqualTo(applied);
    }

    @Test
    @DisplayName("Should return null when unauthenticated")
    void isAppliedUnauthenticatedTest() {
        assertThat(opportunityService.isApplied(1L)).isNull();
    }

    @Test
    @DisplayName("Should return list of opportunities from the given company profile id")
    void findAllByCompanyIdTest() {
        given(opportunityRepository.findAllByCompany_Id(anyLong())).willReturn(Arrays.asList(opportunity, opportunity));
        List<OpportunityDTO> list = opportunityService.findAllByCompanyId(1L);
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return applied opportunity for the given user profile")
    void findAppliedOpportunitiesByProfileIdTest() {
        given(opportunityRepository.findByAppliedUsers_Id(anyLong())).willReturn(Arrays.asList(opportunity, opportunity));
        List<OpportunityDTO> list = opportunityService.findAppliedOpportunitiesByProfileId(1L);
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return list of applied user in the opportunity.")
    @WithMockCustomUser(type = UserType.COMPANY)
    void findAppliedUsersByOpportunityIdTest() {
        opportunity.getAppliedUsers().add(ApplicantProfile.builder().id(1L).build());
        given(opportunityRepository.findById(anyLong())).willReturn(Optional.of(opportunity));
        List<ApplicantProfileDTO> list = opportunityService.findAppliedUsersByOpportunityId(1L);
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw exception when opportunity does not belong to the user.")
    @WithMockCustomUser(type = UserType.COMPANY)
    void findAppliedUsersByOpportunityIdForbiddenUserTest() {
        given(opportunityRepository.findById(anyLong())).willReturn(Optional.of(opportunity));
        opportunity.getCompany().setId(2L);
        Throwable exception = catchThrowable(() -> opportunityService.findAppliedUsersByOpportunityId(1L));
        assertThat(exception).isInstanceOf(UserForbiddenException.class);
    }

    @Test
    @DisplayName("Should delete opportunity by existing id")
    void deleteByIdTest() {
        given(opportunityRepository.findById(anyLong())).willReturn(Optional.of(opportunity));
        doNothing().when(opportunityRepository).delete(any(Opportunity.class));
        opportunityService.deleteById(opportunity.getId());
        verify(opportunityRepository).findById(opportunity.getId());
        verify(opportunityRepository).delete(opportunity);
    }

    @Test
    @DisplayName("Should toggle active state")
    @WithMockCustomUser(type = UserType.COMPANY)
    void toggleActiveTest() {
        given(opportunityRepository.findById(anyLong())).willReturn(Optional.of(opportunity));
        given(opportunityRepository.save(any(Opportunity.class))).willAnswer(invocation -> invocation.getArguments()[0]);
        opportunityService.toggleActive(opportunity.getId());
        assertThat(opportunity.getActive()).isFalse();
        verify(opportunityRepository).save(opportunity);
    }

    @Test
    @DisplayName("Should update opportunity")
    @WithMockCustomUser(type = UserType.COMPANY)
    void patchByIdTest() {
        given(opportunityRepository.findById(anyLong())).willReturn(Optional.of(opportunity));
        given(opportunityRepository.save(any(Opportunity.class))).willAnswer(invocation -> invocation.getArguments()[0]);
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("Cyberspace Security Analyst")
            .build();
        Opportunity updated = opportunityService.patch(this.opportunity.getId(), dto);
        assertThat(updated.getName()).isEqualTo(dto.getName());
        verify(opportunityRepository).save(any(Opportunity.class));
    }

    @Test
    @DisplayName("Should update opportunity and requirements list")
    @WithMockCustomUser(type = UserType.COMPANY)
    void patchByIdWithRequirementsTest() {
        given(opportunityRepository.findById(anyLong())).willReturn(Optional.of(opportunity));
        given(opportunityRepository.save(any(Opportunity.class))).willAnswer(invocation -> invocation.getArguments()[0]);
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("Cyberspace Security Analyst")
            .requirements(Collections.singletonList(Requirement.builder()
                .name("Requerimento")
                .knowledgeLevel(KnowledgeLevel.BASIC)
                .build()))
            .build();
        Opportunity updated = opportunityService.patch(this.opportunity.getId(), dto);
        assertThat(updated.getRequirements()).isEqualTo(dto.getRequirements());
        verify(opportunityRepository).save(any(Opportunity.class));
    }
}