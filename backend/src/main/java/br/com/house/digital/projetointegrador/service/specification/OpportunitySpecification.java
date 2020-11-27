package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.Opportunity_;
import br.com.house.digital.projetointegrador.model.Requirement;
import br.com.house.digital.projetointegrador.model.Requirement_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class OpportunitySpecification extends BaseSpecification<Opportunity> {

    public static Specification<Opportunity> nameContains(String value) {
        return (root, query, builder) -> buildLikeQuery(Opportunity_.name, value, root, builder);
    }

    public static Specification<Opportunity> locationContains(String value) {
        return (root, query, builder) -> buildLikeQuery(Opportunity_.location, value, root, builder);
    }

    public static Specification<Opportunity> benefitsContains(String value) {
        return (root, query, builder) -> buildLikeQuery(Opportunity_.benefits, value, root, builder);
    }

    public static Specification<Opportunity> requirementContains(String value) {
        return (root, query, builder) -> {
            Join<Opportunity, Requirement> join = root.join(Opportunity_.requirements);
            return builder.like(
                builder.lower(join.get(Requirement_.name)),
                likeStringFormat(value)
            );
        };
    }

    public static Specification<Opportunity> isActive(boolean value) {
        return (root, query, builder) -> builder.equal(root.get(Opportunity_.active), value);
    }

}
