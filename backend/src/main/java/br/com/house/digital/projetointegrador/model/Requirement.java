package br.com.house.digital.projetointegrador.model;

import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Requirement extends AbstractEntity<Long> {

    @NotBlank
    @Column(length = 30, nullable = false)
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 12, nullable = false)
    private KnowledgeLevel knowledgeLevel;

}
