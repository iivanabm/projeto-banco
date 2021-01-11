package com.bancoapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_conta")
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "conta_titular", nullable = false)
	private String titular;

	@Column(name = "conta_numero")
	private Long numero;

	@Column(name = "titular_cpf", nullable = false)
	private String cpf;

	@Column(name = "conta_saldo", nullable = false)
	private Double saldo;

	public Conta() {
	}

	public Conta(String titular, Long numero, String cpf, Double saldo) {
		this.titular = titular;
		this.numero = numero;
		this.cpf = cpf;
		this.saldo = saldo;
	}


}
