package com.bancoapi.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bancoapi.dto.ContaDTO;
import com.bancoapi.dto.TransferenciaDTO;
import com.bancoapi.services.ContaService;


@RestController
@RequestMapping(value = "/conta")
@CrossOrigin("http://localhost:4200")
public class ContaResource {

	@Autowired
	private ContaService contaService;
	
	@GetMapping
	public ResponseEntity<List<ContaDTO>> listarContas(){
		List<ContaDTO> contaDTO = contaService.listarContas();
		return ResponseEntity.ok().body(contaDTO);
	}
	
	@GetMapping(value = "/filtrar")
	public ResponseEntity<Page<ContaDTO>> listarContasPaginadas(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "3") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "titular") String direction,
			@RequestParam(value = "orderBy", defaultValue = "ASC") String orderBy
			){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ContaDTO> listaPaginada = contaService.listarContasPaginadas(pageRequest);
		return ResponseEntity.ok().body(listaPaginada);
	}
	
	@GetMapping(value = "/{numero}")
	public ResponseEntity<ContaDTO> encontrarConta(@PathVariable Long numero){
		ContaDTO entidade = contaService.encontrarConta(numero);
		return ResponseEntity.ok().body(entidade);
	}
	
	@PostMapping
	public ResponseEntity<ContaDTO> criarConta(@Valid @RequestBody ContaDTO contaDTO) {
		contaDTO = contaService.criarConta(contaDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numero}")
				.buildAndExpand(contaDTO.getNumero()).toUri();
		return ResponseEntity.created(uri).body(contaDTO);
	}
	
	@PutMapping(value = "/depositar/{numero}/{valor}")
	public ResponseEntity<ContaDTO> depositar(@PathVariable Long numero, @PathVariable Double valor, @Valid @RequestBody ContaDTO dto){
		dto = contaService.depositar(valor, numero);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/sacar/{numero}/{valor}")
	public ResponseEntity<ContaDTO> sacar(@PathVariable Long numero, @PathVariable Double valor, @Valid @RequestBody ContaDTO dto){
		dto = contaService.sacar(valor, numero);
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/transferir")
	public ResponseEntity<ContaDTO> transferir(@RequestBody TransferenciaDTO dto){
		contaService.transferir(dto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ContaDTO> apagar(@PathVariable Long id){
		contaService.apagar(id);
		return ResponseEntity.noContent().build();
	}
	
    
}
