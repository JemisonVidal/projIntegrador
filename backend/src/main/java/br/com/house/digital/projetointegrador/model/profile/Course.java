package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "courses")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Course extends AbstractEntity<Long> {

    @NotBlank
    @Column(length = 100, nullable = false)
    private String institution;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String name;

    @Positive(message = "Carga horária deve ser um número positivo")
    private Integer workLoad; // Carga horária (horas)

    @Min(1)
    @Max(12)
    private Integer conclusionMonth;

    @NotNull
    @Min(1900)
    @Column(nullable = false)
    private Integer conclusionYear;

}
