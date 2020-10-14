package br.com.house.digital.projetointegrador.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Skill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String name;
	private String experienceTime;
	private Integer knowledgeLevel;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "profile_id")
	private Profile profile;

	public KnowledgeLevel getKnowledgeLevel() {
		return KnowledgeLevel.toEnum(knowledgeLevel);
	}

	public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) {
		this.knowledgeLevel = knowledgeLevel.getId();
	}

}
