package com.bancoapi.services.exceptions;

public class SaldoInsuficienteParaAberturaDeNovaContaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteParaAberturaDeNovaContaException(String mensagem) {
		super(mensagem);
	}

}