package com.ecomerce.projeto.controller.form;

import com.ecomerce.projeto.model.Cidade;
import com.ecomerce.projeto.model.Estado;
import com.ecomerce.projeto.repository.CidadeRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CidadeForm {
	
	private String nome;
	private Estado estado;
	
	public Cidade toCidade() {
		return new Cidade(null, nome, estado);
	}
	
	public Cidade atualizar(Long id, CidadeRepository repository) {
		Cidade cidade = repository.getReferenceById(id);
		cidade.setNome(this.nome);
		cidade.setEstado(this.estado);
		
		return cidade;
	}

}
