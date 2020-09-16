package br.com.house.digital.projetointegrador.model;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginInfo {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
