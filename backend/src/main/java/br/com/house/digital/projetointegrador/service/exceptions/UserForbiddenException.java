package br.com.house.digital.projetointegrador.service.exceptions;

public class UserForbiddenException extends RuntimeException {
    public UserForbiddenException(String s) {
        super(s);
    }
}
