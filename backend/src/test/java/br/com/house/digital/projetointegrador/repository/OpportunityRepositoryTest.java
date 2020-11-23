package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles({"test"})
class OpportunityRepositoryTest {

    CompanyProfile company;
    Opportunity opportunity;
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private CompanyProfileRepository companyProfileRepository;
    @Autowired
    private ApplicantProfileRepository applicantProfileRepository;

    @BeforeEach
    void setUp() {
        opportunityRepository.deleteAll();
        companyProfileRepository.deleteAll();
        company = CompanyProfile.builder()
            .id(1L)
            .name("The Restaurant at the End of the Universe")
            .build();
        companyProfileRepository.save(company);
        opportunity = Opportunity.builder()
            .company(company)
            .name("Back-End Java Developer")
            .active(true)
            .description("Back-End Developer")
            .location("End of the Universe")
            .appliedUsers(new ArrayList<>())
            .build();
        opportunityRepository.save(opportunity);
    }

    @Test
    void findByNameContainingIgnoreCaseTest() {
        opportunityRepository.save(Opportunity.builder()
            .company(company)
            .name("Front-End React Developer")
            .active(true)
            .description("Front-End React Developer")
            .location("End of the Universe")
            .appliedUsers(new ArrayList<>())
            .build());
        Page<Opportunity> page = opportunityRepository.findByNameContainingIgnoreCase("java", PageRequest.of(0, 20));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).contains(opportunity);
    }

    @Test
    void findAllByCompanyIdTest() {
        List<Opportunity> list = opportunityRepository.findAllByCompany_Id(company.getId());
        assertThat(list).hasSize(1);
        assertThat(list).contains(opportunity);
    }

    @Test
    void findAllByCompanyIdEmptyTest() {
        List<Opportunity> list = opportunityRepository.findAllByCompany_Id(2L);
        assertThat(list).isEmpty();
    }

    @Test
    void findByAppliedUsers_Id() {
        ApplicantProfile profile = ApplicantProfile.builder().id(2L).name("Arthur Dent").build();
        applicantProfileRepository.save(profile);
        opportunity.getAppliedUsers().add(profile);
        opportunityRepository.save(opportunity);
        assertThat(opportunityRepository.findByAppliedUsers_Id(profile.getId())).contains(opportunity);
    }

    @Test
    void existsByIdAndAppliedUsers_Id() {
        ApplicantProfile profile = ApplicantProfile.builder().id(2L).name("Arthur Dent").build();
        applicantProfileRepository.save(profile);
        opportunity.getAppliedUsers().add(profile);
        opportunityRepository.save(opportunity);
        assertThat(opportunityRepository.existsByIdAndAppliedUsers_Id(opportunity.getId(), profile.getId())).isTrue();
    }
}