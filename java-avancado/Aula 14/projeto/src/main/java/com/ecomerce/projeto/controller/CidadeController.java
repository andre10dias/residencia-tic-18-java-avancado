package com.ecomerce.projeto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.projeto.controller.dto.CidadeDTO;
import com.ecomerce.projeto.model.Cidade;
import com.ecomerce.projeto.service.CidadeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	CidadeService service;
	
	@GetMapping
	public Page<CidadeDTO> listarCidades(
    		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10
	) Pageable paginacao) {
		Page<Cidade> listaCidades = service.listarCidades(paginacao);
		
		log.info("[CidadeController]: listarCidades");
		
		Page<CidadeDTO> listaCidadesDto = listaCidades.map(CidadeDTO::new);
		
		return listaCidadesDto;
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Cidade> getCidadeById(@PathVariable Long id) {
        Optional<Cidade> cidade = service.pegarPorId(id);
        return cidade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cidade> createCidade(@RequestBody Cidade cidade) {
        Cidade createdCidade = service.cadastrar(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> updateCidade(@PathVariable Long id, @RequestBody Cidade updatedCidade) {
        Optional<Cidade> cidade = service.atualizar(id, updatedCidade);
        return cidade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCidade(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/cidade/{estadoId}")
	public List<Cidade> pegarCidadesPorEstado(@PathVariable Long estadoId) {
		List<Cidade> listaCidades = service.pegarCidadesPorEstado(estadoId);
		return listaCidades;
	}

}
