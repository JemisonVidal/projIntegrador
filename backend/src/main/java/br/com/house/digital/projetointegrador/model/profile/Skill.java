package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "skills")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Skill extends AbstractEntity<Long> {

	@NotBlank
	@Column(nullable = false)
	private String name;

	private Integer experienceYears;

	@NotNull
	@Column(nullable = false)
	private Integer knowledgeLevel;

	public KnowledgeLevel getKnowledgeLevel() {
		return KnowledgeLevel.toEnum(knowledgeLevel);
	}

	public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) {
		this.knowledgeLevel = knowledgeLevel.getId();
	}

}
