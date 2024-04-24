package com.ecomerce.projeto.controller;

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

import com.ecomerce.projeto.controller.dto.EstadoDTO;
import com.ecomerce.projeto.model.Estado;
import com.ecomerce.projeto.service.EstadoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	EstadoService service;
	
	@GetMapping
	public Page<EstadoDTO> listarEstados(
    		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10
	) Pageable paginacao) {
		Page<Estado> listaEstados = service.listarEstados(paginacao);
		
		log.info("[EstadoController]: listarEstados");
		
		Page<EstadoDTO> listaEstadosDto = listaEstados.map(EstadoDTO::new);
		
		return listaEstadosDto;
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Estado> getEstadoById(@PathVariable Long id) {
        Optional<Estado> estado = service.pegarPorId(id);
        return estado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estado> createEstado(@RequestBody Estado estado) {
        Estado createdEstado = service.cadastrar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEstado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> updateEstado(@PathVariable Long id, @RequestBody Estado updatedEstado) {
        Optional<Estado> estado = service.atualizar(id, updatedEstado);
        return estado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
