package br.com.house.digital.projetointegrador.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfileDTO extends ProfileDTO {

    private Long id;

    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    @NotBlank
    private String category;

}
