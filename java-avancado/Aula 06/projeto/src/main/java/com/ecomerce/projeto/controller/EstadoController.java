package com.ecomerce.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecomerce.projeto.controller.dto.EstadoDTO;
import com.ecomerce.projeto.controller.form.EstadoForm;
import com.ecomerce.projeto.model.Estado;
import com.ecomerce.projeto.repository.EstadoRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	EstadoRepository repository;
	
	@GetMapping
	public List<Estado> listarEstados() {
		List<Estado> listaEstados = repository.findAll();
		return listaEstados;
	}
	
	@PostMapping()
	public ResponseEntity<EstadoDTO> cadastrar(@RequestBody EstadoForm form, UriComponentsBuilder uriBuilder) {
		Estado estado = form.toEstado();
		repository.save(estado);
		
		URI uri = uriBuilder.path("/estado/{id}").buildAndExpand(estado.getId()).toUri();
		return ResponseEntity.created(uri).body(new EstadoDTO(estado));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstadoDTO> pegarPorId(@PathVariable Long id) {
		//Optional -> Pode ser que tenha o registro pode ser que não tenha
		//Elimina o erro caso o parâmetro passado não exista
		Optional<Estado> estado = repository.findById(id);
		
		if (estado.isPresent()) {
			return ResponseEntity.ok(new EstadoDTO(estado.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long id, @RequestBody EstadoForm form) {
		Optional<Estado> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			Estado estado = form.atualizar(id, repository);
			return ResponseEntity.ok(new EstadoDTO(estado));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Estado> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
