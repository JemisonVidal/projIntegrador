package br.com.house.digital.projetointegrador.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JWTResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final User user;
    private final String token;

}
