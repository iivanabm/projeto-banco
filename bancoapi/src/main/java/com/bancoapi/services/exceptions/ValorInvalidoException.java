package com.bancoapi.services.exceptions;

public class ValorInvalidoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ValorInvalidoException(String mensagem) {
		super(mensagem);
	}

}