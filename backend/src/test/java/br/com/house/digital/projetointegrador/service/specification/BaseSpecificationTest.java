package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.AbstractEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class BaseSpecificationTest<P extends AbstractEntity<Long>> {

    protected P entity1;
    protected P entity2;

    protected void assertOnlyFirstIn(List<P> results) {
        assertThat(entity1).isIn(results);
        assertThat(entity2).isNotIn(results);
    }

    protected void assertOnlySecondIn(List<P> results) {
        assertThat(entity1).isNotIn(results);
        assertThat(entity2).isIn(results);
    }

    protected void assertBothIn(List<P> results) {
        assertThat(entity1).isIn(results);
        assertThat(entity2).isIn(results);
    }

    protected void assertBothNotIn(List<P> results) {
        assertThat(entity1).isNotIn(results);
        assertThat(entity2).isNotIn(results);
    }
}
