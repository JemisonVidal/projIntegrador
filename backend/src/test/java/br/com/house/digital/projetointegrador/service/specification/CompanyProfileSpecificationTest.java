package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.repository.CompanyProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.house.digital.projetointegrador.service.specification.CompanyProfileSpecification.categoryContains;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
class CompanyProfileSpecificationTest extends BaseSpecificationTest<CompanyProfile> {

    @Autowired
    private CompanyProfileRepository repository;

    @BeforeEach
    void setUp() {
        entity1 = repository.save(CompanyProfile.builder()
            .name("Coding Coders")
            .title("Coding Coders Code Company")
            .category("Coding")
            .location("São Paulo, SP")
            .build());

        entity2 = repository.save(CompanyProfile.builder()
            .name("Developing Developers")
            .title("Developing Developers Dev Company")
            .category("Developing")
            .location("São Paulo, SP")
            .build());
    }

    @Test
    void findByCategoryTest() {
        List<CompanyProfile> results = repository.findAll(categoryContains("coding"));
        assertOnlyFirstIn(results);
    }

}