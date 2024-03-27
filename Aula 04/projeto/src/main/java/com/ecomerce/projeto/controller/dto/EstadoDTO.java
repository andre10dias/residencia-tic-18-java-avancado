package com.ecomerce.projeto.controller.dto;

import java.util.ArrayList;
import java.util.List;

import com.ecomerce.projeto.model.Estado;

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
	private String nome;
	private List<CidadeDTO> cidades;
	
	public EstadoDTO(Estado estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
		this.cidades = new ArrayList<>();
	}

}
