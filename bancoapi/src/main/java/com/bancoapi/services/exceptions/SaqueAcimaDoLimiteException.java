package com.bancoapi.services.exceptions;

public class SaqueAcimaDoLimiteException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public SaqueAcimaDoLimiteException(String mensagem) {
		super(mensagem);
	}

}
