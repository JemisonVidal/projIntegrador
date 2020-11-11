package br.com.house.digital.projetointegrador.dto.profile;

import br.com.house.digital.projetointegrador.model.profile.Course;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.model.profile.WorkExperience;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantProfileDTO extends ProfileDTO {

    private String name;
    private Double desiredSalary;
    private String phoneNumber;
    @JsonIgnoreProperties("id")
    private List<Skill> skills = new ArrayList<>();
    @JsonIgnoreProperties("id")
    private List<Course> courses = new ArrayList<>();
    @JsonIgnoreProperties("id")
    private List<WorkExperience> workExperiences = new ArrayList<>();

}
