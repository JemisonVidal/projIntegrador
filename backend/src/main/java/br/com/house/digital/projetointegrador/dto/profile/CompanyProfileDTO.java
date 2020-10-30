package br.com.house.digital.projetointegrador.dto.profile;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfileDTO extends ProfileDTO {

    @NotBlank
    @Length(max = 50)
    private String name;

    @CNPJ
    private String cnpj;

    @Length(max = 100)
    private String shortDescription;

    @NotNull
    private LocalDate startDate;

    @NotBlank
    private String category;

}
