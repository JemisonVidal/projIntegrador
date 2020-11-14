package br.com.house.digital.projetointegrador.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfileDTO extends ProfileDTO {

    @NotBlank
    @Length(max = 100)
    private String name;

    @CNPJ
    private String cnpj;

    @NotNull
    private LocalDate startDate;

    @NotBlank
    private String category;

}
