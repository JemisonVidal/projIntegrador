package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.AbstractEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public abstract class BaseSpecification<T extends AbstractEntity<Long>> {

    protected static <T extends AbstractEntity<Long>> Predicate buildLikeQuery(String property,
                                                                               String value,
                                                                               Root<T> root,
                                                                               CriteriaBuilder builder) {
        return builder.like(
            builder.lower(root.get(property)),
            likeStringFormat(value)
        );
    }

    protected static <T extends AbstractEntity<Long>> Predicate buildLikeQuery(SingularAttribute<T, String> property,
                                                                               String value,
                                                                               Root<T> root,
                                                                               CriteriaBuilder builder) {
        return builder.like(
            builder.lower(root.get(property)),
            likeStringFormat(value)
        );
    }

    protected static String likeStringFormat(String value) {
        return "%" + value.toLowerCase() + "%";
    }

}
