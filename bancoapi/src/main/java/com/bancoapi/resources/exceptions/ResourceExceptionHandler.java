package com.bancoapi.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bancoapi.services.exceptions.ResourceNotFoundException;
import com.bancoapi.services.exceptions.SaldoInsuficienteException;
import com.bancoapi.services.exceptions.SaldoInsuficienteParaAberturaDeNovaContaException;
import com.bancoapi.services.exceptions.SaqueAcimaDoLimiteException;
import com.bancoapi.services.exceptions.ValorInvalidoException;


@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> contaNaoEncontrada(ResourceNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Conta não encontrada.");
		err.setTrace("");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(SaqueAcimaDoLimiteException.class)
	public ResponseEntity<StandardError> saqueAcimaDoLimite(SaqueAcimaDoLimiteException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Operação de transferência tem um limite máximo de 500 por operação.");
		err.setTrace("");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<StandardError> saldoInsuficiente(SaldoInsuficienteException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Seu saldo é insuficiente.");
		err.setTrace("");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ValorInvalidoException.class)
	public ResponseEntity<StandardError> valorInvalido(ValorInvalidoException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("O valor não pode ser negativo.");
		err.setTrace("");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(SaldoInsuficienteParaAberturaDeNovaContaException.class)
	public ResponseEntity<StandardError> saldoInsuficienteParaAberturaDeConta(
			SaldoInsuficienteParaAberturaDeNovaContaException e, HttpServletRequest request) {
		
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Saldo insuficiente para abertura de nova conta.");
		err.setTrace("");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation exception");
		err.setTrace("");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}
	
}
