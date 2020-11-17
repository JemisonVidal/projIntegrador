package br.com.house.digital.projetointegrador.dto.opportunity;

import br.com.house.digital.projetointegrador.model.Requirement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewOpportunityDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

    private String benefits;
    private Double salary;
    private String text;
    private Boolean active;

    @NotNull
    private List<@Valid Requirement> requirements;

}
