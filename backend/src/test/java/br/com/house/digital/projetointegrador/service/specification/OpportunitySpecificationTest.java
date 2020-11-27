package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.Requirement;
import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import br.com.house.digital.projetointegrador.repository.OpportunityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.house.digital.projetointegrador.service.specification.OpportunitySpecification.*;
import static java.util.Collections.singletonList;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
class OpportunitySpecificationTest extends BaseSpecificationTest<Opportunity> {

    @Autowired
    private OpportunityRepository repository;

    @BeforeEach
    void setUp() {
        entity1 = repository.save(Opportunity.builder()
            .name("Dev Java Full-Stack")
            .location("São Paulo, SP")
            .description("Lorem ipsum")
            .active(true)
            .benefits("Plano de saúde")
            .requirements(singletonList(Requirement.builder().name("Java").knowledgeLevel(KnowledgeLevel.ADVANCED).build()))
            .build()
        );
        entity2 = repository.save(Opportunity.builder()
            .name("Dev React Front-End")
            .location("Curitiba, PR")
            .description("Lorem ipsum")
            .active(true)
            .benefits("Vale-refeição")
            .requirements(singletonList(Requirement.builder().name("React").knowledgeLevel(KnowledgeLevel.ADVANCED).build()))
            .build()
        );
    }

    @Test
    void findByNameTest() {
        List<Opportunity> results = repository.findAll(nameContains("java"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findByLocationTest() {
        List<Opportunity> results = repository.findAll(locationContains("curitiba"));
        assertOnlySecondIn(results);
    }

    @Test
    void findByBenefitsTest() {
        List<Opportunity> results = repository.findAll(benefitsContains("plano"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findByRequirementTest() {
        List<Opportunity> results = repository.findAll(requirementContains("java"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findByLocationAndRequirementTest() {
        List<Opportunity> results = repository.findAll(Specification.where(locationContains("curitiba")).and(requirementContains("java")));
        assertBothNotIn(results);
    }

    @Test
    void findByLocationOrRequirementTest() {
        List<Opportunity> results = repository.findAll(Specification.where(locationContains("curitiba")).or(requirementContains("java")));
        assertBothIn(results);
    }
}