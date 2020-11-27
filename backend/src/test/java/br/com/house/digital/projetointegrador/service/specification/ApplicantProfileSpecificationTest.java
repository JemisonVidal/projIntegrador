package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.Course;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.repository.ApplicantProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.house.digital.projetointegrador.service.specification.ApplicantProfileSpecification.*;
import static java.util.Collections.singletonList;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
class ApplicantProfileSpecificationTest extends BaseSpecificationTest<ApplicantProfile> {

    @Autowired
    private ApplicantProfileRepository repository;

    @BeforeEach
    void setUp() {
        entity1 = repository.save(ApplicantProfile.builder()
            .name("Joana")
            .title("Dev Java Full-Stack")
            .location("São Paulo, SP")
            .skills(singletonList(Skill.builder()
                .name("Java")
                .knowledgeLevel(1)
                .build()
            ))
            .courses(
                singletonList(Course.builder().name("Java").conclusionYear(2020).institution("Java School").build()))
            .build());

        entity2 = repository.save(ApplicantProfile.builder()
            .name("Bruna")
            .title("Dev Front-End")
            .location("Florianópolis, SC")
            .skills(singletonList(Skill.builder()
                .name("CSS")
                .knowledgeLevel(1)
                .build()
            ))
            .courses(singletonList(Course.builder().name("CSS").conclusionYear(2020).institution("CSS School").build()))
            .build());
    }

    @Test
    void findByNameTest() {
        List<ApplicantProfile> results = repository.findAll(nameContains("joana"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findBySkillTest() {
        List<ApplicantProfile> results = repository.findAll(skillContains("java"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findByLocationTest() {
        List<ApplicantProfile> results = repository.findAll(locationContains("são paulo"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findByTitleTest() {
        List<ApplicantProfile> results = repository.findAll(titleContains("full-stack"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findByCourseTest() {
        List<ApplicantProfile> results = repository.findAll(courseContains("java"));
        assertOnlyFirstIn(results);
    }

    @Test
    void findBySkillOrNameTest() {
        List<ApplicantProfile> results = repository.findAll(
            Specification.where(skillContains("css").or(nameContains("joana"))));
        assertBothIn(results);
    }

    @Test
    void findBySkillAndNameTest() {
        List<ApplicantProfile> results = repository.findAll(
            Specification.where(skillContains("css").and(nameContains("joana"))));
        assertBothNotIn(results);
    }
}