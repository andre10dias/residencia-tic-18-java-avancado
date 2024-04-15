package com.ecomerce.projeto.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.ecomerce.projeto.model.Estado;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EstadoDTO {
	
	private Long id;
	
	@NotEmpty(message = "O campo Nome é obrigatório.")
	@Length(min = 3, max = 50, message = "O campo Nome deve ter entre 3 e 50 caracteres.")
	private String nome;
	
	private List<CidadeDTO> cidades;
	
	public EstadoDTO(Estado estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
		this.cidades = new ArrayList<>();
	}

}
