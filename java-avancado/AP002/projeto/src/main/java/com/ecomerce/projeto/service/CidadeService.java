package com.ecomerce.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecomerce.projeto.model.Cidade;
import com.ecomerce.projeto.repository.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	@Cacheable("cidades")
	public Page<Cidade> listarCidades(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Cidade cadastrar(Cidade u) {
		return repository.save(u);
	}
	
	public Optional<Cidade> pegarPorId(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Cidade> atualizar(Long id, Cidade obj) {
		return repository.findById(id).map(cidade -> {
			cidade.setNome(obj.getNome());
			cidade.setEstado(obj.getEstado());
            return repository.save(cidade);
        });
	}
	
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	public List<Cidade> pegarCidadesPorEstado(@PathVariable Long estadoId) {
		List<Cidade> listaCidades = repository.findCidadesByEstadoId(estadoId);
		return listaCidades;
	}

}
