package br.com.house.digital.projetointegrador.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@AllArgsConstructor
public class LoginDTO {

    @NotBlank String email;

    @NotBlank String password;

}
