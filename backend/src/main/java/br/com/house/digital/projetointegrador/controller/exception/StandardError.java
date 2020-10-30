package br.com.house.digital.projetointegrador.controller.exception;

import java.io.Serializable;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer status;
	private String msg;
	private final Long timeStamp = System.currentTimeMillis();
}
