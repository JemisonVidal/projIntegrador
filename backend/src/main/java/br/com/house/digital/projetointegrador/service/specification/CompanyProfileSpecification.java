package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile_;
import br.com.house.digital.projetointegrador.model.profile.Profile;
import org.springframework.data.jpa.domain.Specification;

public class CompanyProfileSpecification extends ProfileSpecification<CompanyProfile> {

    public static <P extends Profile> Specification<P> categoryContains(String value) {
        return (root, query, builder) -> buildLikeQuery(CompanyProfile_.CATEGORY, value, root, builder);
    }

}
