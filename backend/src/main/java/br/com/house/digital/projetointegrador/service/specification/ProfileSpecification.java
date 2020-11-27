package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.profile.Profile;
import br.com.house.digital.projetointegrador.model.profile.Profile_;
import org.springframework.data.jpa.domain.Specification;

public abstract class ProfileSpecification<P extends Profile> extends BaseSpecification<P> {

    public static <P extends Profile> Specification<P> nameContains(String value) {
        return (root, query, builder) -> buildLikeQuery(Profile_.NAME, value, root, builder);
    }

    public static <P extends Profile> Specification<P> locationContains(String value) {
        return (root, query, builder) -> buildLikeQuery(Profile_.LOCATION, value, root, builder);
    }

    public static <P extends Profile> Specification<P> titleContains(String value) {
        return (root, query, builder) -> buildLikeQuery(Profile_.TITLE, value, root, builder);
    }

}
