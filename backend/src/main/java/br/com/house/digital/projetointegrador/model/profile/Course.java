package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Course extends AbstractEntity<Long> {

    private String institution;
    private String name;
    private String workLoad;
    private Integer conclusionYear;

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "profile_id")
//	private ApplicantProfile profile;

}
