package com.ecomerce.projeto.controller.form;

import java.util.List;

import com.ecomerce.projeto.model.Estado;
import com.ecomerce.projeto.model.Cidade;
import com.ecomerce.projeto.repository.EstadoRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EstadoForm {
	
	private String nome;
	private List<Cidade> cidades;
	
	public Estado toEstado() {
		return new Estado(null, nome, cidades);
	}
	
	public Estado atualizar(Long id, EstadoRepository repository) {
		Estado estado = repository.getReferenceById(id);
		estado.setNome(this.nome);
		estado.setCidades(this.cidades);
		
		return estado;
	}

}
