package br.com.house.digital.projetointegrador.dto.opportunity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDTO extends NewOpportunityDTO {

    private Long companyId;
    private String companyName;

}
