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

import com.ecomerce.projeto.controller.dto.CategoriaDTO;
import com.ecomerce.projeto.model.Categoria;
import com.ecomerce.projeto.service.CategoriaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaService service;
	
//	@GetMapping("/faker")
//	public ResponseEntity<List<CategoriaDTO>> listarFaker() {
//		List<Categoria> listaCategorias = service.getListaCategoriaFaker();
//		
//		log.info("[CategoriaController]: listarCategorias");
//		
//		List<CategoriaDTO> listaCategoriasDto = listaCategorias.stream().map(CategoriaDTO::new)
//				.collect(Collectors.toList());
//		
//		return ResponseEntity.ok(listaCategoriasDto);
//	}
	
	@GetMapping
	public Page<CategoriaDTO> listarCategorias(
    		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10
	) Pageable paginacao) {
		Page<Categoria> listaCategorias = service.listarCategorias(paginacao);
		
		log.info("[CategoriaController]: listarCategorias");
		
		Page<CategoriaDTO> listaCategoriasDto = listaCategorias.map(CategoriaDTO::new);
		
		return listaCategoriasDto;
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoria = service.pegarPorId(id);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        Categoria createdCategoria = service.cadastrar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria updatedCategoria) {
        Optional<Categoria> categoria = service.atualizar(id, updatedCategoria);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
