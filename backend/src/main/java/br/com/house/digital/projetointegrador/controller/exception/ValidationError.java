package br.com.house.digital.projetointegrador.controller.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private final List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg) {
		super(status, msg);
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
