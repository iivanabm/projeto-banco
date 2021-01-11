package com.bancoapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancoapi.domain.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{
	
	Optional<Conta> findByNumero(Long numero);
	
}
