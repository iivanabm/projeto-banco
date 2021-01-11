package com.bancoapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancoapi.domain.Conta;
import com.bancoapi.dto.ContaDTO;
import com.bancoapi.dto.TransferenciaDTO;
import com.bancoapi.repositories.ContaRepository;
import com.bancoapi.services.exceptions.DatabaseException;
import com.bancoapi.services.exceptions.ResourceNotFoundException;
import com.bancoapi.services.exceptions.SaldoInsuficienteException;
import com.bancoapi.services.exceptions.SaqueAcimaDoLimiteException;
import com.bancoapi.services.exceptions.ValorInvalidoException;


@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Transactional(readOnly = true)
	public List<ContaDTO> listarContas(){
		List<Conta> lista = contaRepository.findAll();
		return lista.stream().map(x -> new ContaDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<ContaDTO>listarContasPaginadas(PageRequest pageRequest){
		Page<Conta> listaPaginada = contaRepository.findAll(pageRequest);
		return listaPaginada.map(x -> new ContaDTO(x));
	}
	

	@Transactional(readOnly = true)
	public ContaDTO encontrarConta(Long numero) {
		Optional<Conta> obj = contaRepository.findByNumero(numero);
		Conta entidade = obj.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada."));
		return new ContaDTO(entidade);
	}

	@Transactional
	public ContaDTO criarConta(ContaDTO contaDTO) {
		Conta entidade = new Conta();
		entidade.setTitular(contaDTO.getTitular());
		entidade.setCpf(contaDTO.getCpf());
		entidade.setSaldo(contaDTO.getSaldo());
		entidade.setNumero(criarNumero());
		entidade = contaRepository.save(entidade);
		return new ContaDTO(entidade);
	}

	@Transactional
	public ContaDTO depositar(Double valor, Long numero) {
		Optional<Conta> obj = contaRepository.findByNumero(numero);
		Conta entidade = obj.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada."));
		Double novoSaldo = 0d;
		novoSaldo += entidade.getSaldo() + valor;
		entidade.setSaldo(novoSaldo);
		return new ContaDTO(entidade);
	}

	@Transactional
	public ContaDTO sacar(Double valor, Long numero) {
		Optional<Conta> obj = contaRepository.findByNumero(numero);
		Conta entidade = obj.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada."));
		verificarSaqueAcimaDoLimite(valor);
		verificarValorInvalido(valor);
		if (valor > entidade.getSaldo()) {
			throw new SaldoInsuficienteException("Seu saldo é insuficiente.");
		} else {
			Double novoSaldo = entidade.getSaldo() - valor;
			entidade.setSaldo(novoSaldo);
			return new ContaDTO(entidade);
		}
	}

	@Transactional
	public void transferir(TransferenciaDTO transferir) {
		sacar(transferir.getValor(), transferir.getContaOrigem());
		depositar(transferir.getValor(), transferir.getContaDestino());
	}
	
	public void apagar(Long id) {
		try {
			contaRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Conta não encontrada: ID: " + id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de Integridade.");
		}
	}
	
	
	private void verificarSaqueAcimaDoLimite(Double valor) {
		if (valor > 500) {
			throw new SaqueAcimaDoLimiteException(
					"Operação de transferência tem um limite máximo de 500 por operação.");
		}

	}
	
	private void verificarValorInvalido(Double valor) {
		if (valor < 0) {
			throw new ValorInvalidoException("O valor não pode ser negativo.");
		}
	}

	private Long criarNumero() {
		Long numeroDaConta = (long) (Math.random() * 1000000);
		return numeroDaConta;
	}

}
