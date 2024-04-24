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

import com.ecomerce.projeto.controller.dto.ProdutoDTO;
import com.ecomerce.projeto.model.Produto;
import com.ecomerce.projeto.service.ProdutoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	ProdutoService service;
	
//	@GetMapping("/faker")
//	public ResponseEntity<List<ProdutoDTO>> listarFaker() {
//		List<Produto> listaProdutos = service.getListaProdutoFaker();
//		
//		log.info("[ProdutoController]: listarProdutos");
//		
//		List<ProdutoDTO> listaProdutosDto = listaProdutos.stream().map(ProdutoDTO::new)
//				.collect(Collectors.toList());
//		
//		return ResponseEntity.ok(listaProdutosDto);
//	}
	
	@GetMapping
	public Page<ProdutoDTO> listarProdutos(
    		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10
	) Pageable paginacao) {
		Page<Produto> listaProdutos = service.listarProdutos(paginacao);
		
		log.info("[ProdutoController]: listarProdutos");
		
		Page<ProdutoDTO> listaProdutosDto = listaProdutos.map(ProdutoDTO::new);
		
		return listaProdutosDto;
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = service.pegarPorId(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto createdProduto = service.cadastrar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto updatedProduto) {
        Optional<Produto> produto = service.atualizar(id, updatedProduto);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/produto/{categoriaId}")
	public List<Produto> pegarProdutosPorCategoria(@PathVariable Long categoriaId) {
		List<Produto> listaProdutos = service.pegarProdutosPorCategoria(categoriaId);
		return listaProdutos;
	}

}
