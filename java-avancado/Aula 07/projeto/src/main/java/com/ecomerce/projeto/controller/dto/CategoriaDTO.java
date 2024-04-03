package com.ecomerce.projeto.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.ecomerce.projeto.model.Categoria;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoriaDTO {
	
	private Long id;
	
	@NotEmpty(message = "O campo Nome é obrigatório.")
	@Length(min = 3, max = 100, message = "O campo Nome deve ter entre 3 e 100 caracteres.")
	private String nome;
	
	private List<ProdutoDTO> produtos;
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
		this.produtos = new ArrayList<>();
	}

}
