package br.com.house.digital.projetointegrador.dto.profile;

import br.com.house.digital.projetointegrador.model.profile.Course;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.model.profile.WorkExperience;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantProfileDTO extends ProfileDTO {

    private String name;
    private String locationWanted;
    private Double desiredSalary;
    private String phoneNumber;
    private List<@Valid Skill> skills;
    private List<@Valid Course> courses;
    private List<@Valid WorkExperience> workExperiences;

}
