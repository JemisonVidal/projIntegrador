package br.com.house.digital.projetointegrador.dto.opportunity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDTO extends NewOpportunityDTO {

    private Long id;
    private Long companyId;
    private String companyName;
    private Boolean isApplied;

}
