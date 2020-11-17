package br.com.house.digital.projetointegrador.service;

import br.com.house.digital.projetointegrador.dto.authentication.LoginDTO;
import br.com.house.digital.projetointegrador.dto.authentication.RegisterDTO;
import br.com.house.digital.projetointegrador.dto.authentication.TokenDTO;
import br.com.house.digital.projetointegrador.model.User;

public interface AuthenticationService {

    TokenDTO authenticate(LoginDTO loginDTO) throws Exception;

    User save(RegisterDTO registerDTO);

}
