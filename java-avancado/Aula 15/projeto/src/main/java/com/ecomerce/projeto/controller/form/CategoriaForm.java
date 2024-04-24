package com.ecomerce.projeto.controller.form;

import java.util.List;
import java.util.Optional;

import com.ecomerce.projeto.model.Categoria;
import com.ecomerce.projeto.model.Produto;
import com.ecomerce.projeto.repository.CategoriaRepository;
import com.ecomerce.projeto.service.CategoriaService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriaForm {
	
	private String nome;
	private List<Produto> produtos;
	
	public Categoria toCategoria() {
		return new Categoria(null, nome, produtos);
	}
	
	public Optional<Categoria> atualizar(Long id, CategoriaService service) {
		Optional<Categoria> categoria = service.pegarPorId(id);
		categoria.get().setNome(this.nome);
		categoria.get().setProdutos(this.produtos);
		
		return categoria;
	}

}
