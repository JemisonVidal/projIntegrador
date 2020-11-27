package br.com.house.digital.projetointegrador.service.specification;

import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.model.profile.Course;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile_;
import br.com.house.digital.projetointegrador.model.profile.Course_;
import br.com.house.digital.projetointegrador.model.profile.Skill_;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

@AllArgsConstructor
public class ApplicantProfileSpecification extends ProfileSpecification<ApplicantProfile> {

    public static Specification<ApplicantProfile> skillContains(String value) {
        return (root, query, builder) -> {
            Join<ApplicantProfile, Skill> join = root.join(ApplicantProfile_.skills);
            return builder.like(builder.lower(join.get(Skill_.name)), likeStringFormat(value));
        };
    }
    public static Specification<ApplicantProfile> courseContains(String value) {
        return (root, query, builder) -> {
            Join<ApplicantProfile, Course> join = root.join(ApplicantProfile_.courses);
            return builder.like(builder.lower(join.get(Course_.name)), likeStringFormat(value));
        };
    }
}
