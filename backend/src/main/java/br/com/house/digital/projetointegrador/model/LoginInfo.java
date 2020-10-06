package br.com.house.digital.projetointegrador.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
