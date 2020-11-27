package br.com.house.digital.projetointegrador.model.profile;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applicants_profiles")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicantProfile extends Profile {

    @Column(length = 50)
    private String locationWanted;

    private Double desiredSalary;

    @Column(length = 20)
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    @Builder.Default
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    @Builder.Default
    private List<Course> courses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    @Builder.Default
    private List<WorkExperience> workExperiences = new ArrayList<>();

}


