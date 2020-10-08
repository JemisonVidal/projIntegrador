package br.com.house.digital.projetointegrador.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class OpportunityDTO {

    @NotBlank String title;

    @NotBlank String description;

    String benefits;
    String salary;
    String text;
    Boolean active;

}
