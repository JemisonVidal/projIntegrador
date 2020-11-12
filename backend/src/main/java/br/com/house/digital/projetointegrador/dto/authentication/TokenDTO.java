package br.com.house.digital.projetointegrador.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    String token;

}
