package com.bancoapi.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.br.CPF;

import com.bancoapi.domain.Conta;

import lombok.Data;

@Data
public class ContaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Pattern(regexp = "[^0-9]*", message="Deve conter apenas letras.")
	@NotBlank(message = "O nome do titular é obrigatório")
	private String titular;

	private Long numero;

	@NotNull(message = "É necessário informar um cpf para abertura de nova conta.")
	@CPF(message = "CPF informado para criação de conta está inválido.")
	private String cpf;

	@Min(value = 50, message = "Saldo insuficiente para abertura de nova conta.")
	@Positive(message ="O valor tem deve ser maior que zero")
	private double saldo;

	public ContaDTO() {
	}

	public ContaDTO(Conta entity) {
		this.id = entity.getId();
		this.titular = entity.getTitular();
		this.numero = entity.getNumero();
		this.cpf = entity.getCpf();
		this.saldo = entity.getSaldo();
	}

	public ContaDTO(Long id, String titular, Long numero, String cpf, double saldo) {
		this.id = id;
		this.titular = titular;
		this.numero = numero;
		this.cpf = cpf;
		this.saldo = saldo;
	}

}