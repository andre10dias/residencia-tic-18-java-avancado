package com.ecomerce.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.projeto.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	public List<Cidade> findCidadesByEstadoId(Long id);

}