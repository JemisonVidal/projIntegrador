package br.com.house.digital.projetointegrador.controller.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fielName;
	private String message;

	public FieldMessage() {

	}

	public FieldMessage(String fielName, String message) {
		super();
		this.fielName = fielName;
		this.message = message;
	}
}
