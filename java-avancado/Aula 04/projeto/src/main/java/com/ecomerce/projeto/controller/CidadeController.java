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

import com.ecomerce.projeto.controller.dto.CidadeDTO;
import com.ecomerce.projeto.controller.form.CidadeForm;
import com.ecomerce.projeto.model.Cidade;
import com.ecomerce.projeto.repository.CidadeRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	CidadeRepository repository;
	
	@GetMapping
	public List<Cidade> listarCidades() {
		List<Cidade> listaCidades = repository.findAll();
		return listaCidades;
	}
	
	@PostMapping()
	public ResponseEntity<CidadeDTO> cadastrar(@RequestBody CidadeForm form, UriComponentsBuilder uriBuilder) {
		Cidade cidade = form.toCidade();
		repository.save(cidade);
		
		URI uri = uriBuilder.path("/cidade/{id}").buildAndExpand(cidade.getId()).toUri();
		return ResponseEntity.created(uri).body(new CidadeDTO(cidade));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> pegarPorId(@PathVariable Long id) {
		//Optional -> Pode ser que tenha o registro pode ser que não tenha
		//Elimina o erro caso o parâmetro passado não exista
		Optional<Cidade> cidade = repository.findById(id);
		
		if (cidade.isPresent()) {
			return ResponseEntity.ok(new CidadeDTO(cidade.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long id, @RequestBody CidadeForm form) {
		Optional<Cidade> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			Cidade cidade = form.atualizar(id, repository);
			return ResponseEntity.ok(new CidadeDTO(cidade));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Cidade> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
