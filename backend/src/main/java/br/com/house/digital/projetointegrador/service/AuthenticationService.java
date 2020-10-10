package br.com.house.digital.projetointegrador.service;

import br.com.house.digital.projetointegrador.model.JWTResponse;
import br.com.house.digital.projetointegrador.dto.LoginDTO;
import br.com.house.digital.projetointegrador.model.User;

public interface AuthenticationService {

    JWTResponse authenticate(LoginDTO loginDTO) throws Exception;

    User save(User user);

}
