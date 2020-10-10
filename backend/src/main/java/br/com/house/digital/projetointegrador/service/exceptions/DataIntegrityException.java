package br.com.house.digital.projetointegrador.service.exceptions;

public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {
		super(msg);
	}

	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}