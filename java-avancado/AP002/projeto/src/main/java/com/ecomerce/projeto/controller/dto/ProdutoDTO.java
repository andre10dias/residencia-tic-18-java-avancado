package com.ecomerce.projeto.controller.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.ecomerce.projeto.model.Produto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoDTO {
	
	private Long id;
	
	@NotEmpty(message = "O campo Nome é obrigatório.")
	@Length(min = 3, max = 100, message = "O campo Nome deve ter entre 3 e 100 caracteres.")
	private String nome;
	
	@NotNull(message = "O campo Preço é obrigatório.")
	private Double preco;
	
	private List<CategoriaDTO> categorias;
	
	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
		this.categorias = produto.getCategorias().stream().map(CategoriaDTO::new).toList();
	}

}
