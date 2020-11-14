package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "work_experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class WorkExperience extends AbstractEntity<Long> {

    @Column(nullable = false)
    @NotBlank
    private String company;

    @Column(nullable = false)
    @NotBlank
    private String position;

    @Column(nullable = false)
    @NotBlank
    private String location;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String activities;

    @PastOrPresent
    private LocalDate initialDate;
    private LocalDate finalDate;

    @Column(nullable = false)
    private boolean acting;

}
