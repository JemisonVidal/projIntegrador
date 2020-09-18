package br.com.house.digital.projetointegrador.service;

import br.com.house.digital.projetointegrador.model.JWTResponse;
import br.com.house.digital.projetointegrador.model.LoginInfo;
import br.com.house.digital.projetointegrador.model.User;

public interface AuthenticationService {

    JWTResponse authenticate(LoginInfo loginInfo) throws Exception;

    User save(User user);

}
