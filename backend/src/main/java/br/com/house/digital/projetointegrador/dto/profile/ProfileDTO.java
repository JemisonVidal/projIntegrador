package br.com.house.digital.projetointegrador.dto.profile;

import br.com.house.digital.projetointegrador.model.profile.Location;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    @NotBlank
    private String text;

    @NotNull
    private Location location;

}
