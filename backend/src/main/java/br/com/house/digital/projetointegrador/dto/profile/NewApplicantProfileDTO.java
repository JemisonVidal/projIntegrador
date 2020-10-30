package br.com.house.digital.projetointegrador.dto.profile;

import br.com.house.digital.projetointegrador.model.profile.Course;
import br.com.house.digital.projetointegrador.model.profile.ExternalLink;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.model.profile.WorkExperience;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewApplicantProfileDTO extends ProfileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double desiredSalary;
    private String phoneNumber;
    @JsonIgnoreProperties("id")
    private Set<ExternalLink> links = new HashSet<>();
    @JsonIgnoreProperties("id")
    private Set<Skill> skills = new HashSet<>();
    @JsonIgnoreProperties("id")
    private Set<Course> courses = new HashSet<>();
    @JsonIgnoreProperties("id")
    private Set<WorkExperience> workExperiences = new HashSet<>();

}
