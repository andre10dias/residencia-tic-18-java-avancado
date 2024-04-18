package com.ecomerce.projeto.controller.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ecomerce.projeto.model.Categoria;
import com.ecomerce.projeto.model.Produto;
import com.ecomerce.projeto.service.CategoriaService;
import com.ecomerce.projeto.service.ProdutoService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoForm {
	
	private String nome;
	private Double preco;
	private Long categoriaId;
	
	public Produto toProduto(List<Categoria> categorias) {
		return new Produto(null, nome, preco, categorias);
	}
	
	public Optional<Produto> atualizar(Long id, ProdutoService produtoService, 
			CategoriaService categoriaService) {
		Optional<Produto> produto = produtoService.pegarPorId(id);
		produto.get().setNome(this.nome);
		produto.get().setPreco(this.preco);
		
		Optional<Categoria> categorias = categoriaService.pegarPorId(categoriaId);
		produto.get().setCategorias(Collections.singletonList(categorias.get()));
		
		return produto;
	}

}
