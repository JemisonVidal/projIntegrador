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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Skills implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String experienceTime;
	private Integer knowledgeLevel;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "profile_id")
	private Profile profile;

	public Skills() {

	}

	public Skills(Integer id, String name, String experienceTime, KnowledgeLevel knowledgeLevel, Profile profile) {
		super();
		this.id = id;
		this.name = name;
		this.experienceTime = experienceTime;
		this.knowledgeLevel = knowledgeLevel.getId();
		this.profile = profile;
	}

	public KnowledgeLevel getKnowledgeLevel() {
		return KnowledgeLevel.toEnum(knowledgeLevel);
	}

	public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) {
		this.knowledgeLevel = knowledgeLevel.getId();
	}

}
