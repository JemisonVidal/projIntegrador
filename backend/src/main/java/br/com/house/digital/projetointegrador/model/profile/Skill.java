package br.com.house.digital.projetointegrador.model.profile;

import java.io.Serializable;

import javax.persistence.*;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import lombok.*;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Skill extends AbstractEntity<Long> {

	private String name;
	private String experienceTime;
	private Integer knowledgeLevel;

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "profile_id")
//	private ApplicantProfile profile;

	public KnowledgeLevel getKnowledgeLevel() {
		return KnowledgeLevel.toEnum(knowledgeLevel);
	}

	public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) {
		this.knowledgeLevel = knowledgeLevel.getId();
	}

}
