package com.bancoapi.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class TransferenciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long contaOrigem;

	@NotNull
	private Long contaDestino;

	@Positive
	@NotNull
	@Max(value = 500, message = "Operação de transferência tem um limite máximo de 500 por operação.")
	private Double valor;

}