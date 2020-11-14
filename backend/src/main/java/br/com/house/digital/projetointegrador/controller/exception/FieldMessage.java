package br.com.house.digital.projetointegrador.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;

}
