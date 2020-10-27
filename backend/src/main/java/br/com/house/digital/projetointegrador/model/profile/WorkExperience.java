package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "work_experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class WorkExperience extends AbstractEntity<Long> {

    private String name;
    private String position;
    private String activities;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private boolean acting;

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "profile_id")
//	private ApplicantProfile profile;

}
