package br.com.house.digital.projetointegrador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;
    private String token;

}
