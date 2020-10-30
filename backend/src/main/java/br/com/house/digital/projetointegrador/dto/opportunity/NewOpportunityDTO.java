package br.com.house.digital.projetointegrador.dto.opportunity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewOpportunityDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String benefits;
    private Double salary;
    private String text;
    private Boolean active;

}
