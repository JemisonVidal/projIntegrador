package br.com.house.digital.projetointegrador.service.exceptions;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
