package com.ecomerce.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Estado;
import com.ecomerce.projeto.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	@Cacheable("estados")
	public Page<Estado> listarEstados(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Estado cadastrar(Estado u) {
		return repository.save(u);
	}
	
	public Optional<Estado> pegarPorId(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Estado> atualizar(Long id, Estado obj) {
		return repository.findById(id).map(estado -> {
			estado.setNome(obj.getNome());
			estado.setCidades(obj.getCidades());
            return repository.save(estado);
        });
	}
	
	public void remover(Long id) {
		repository.deleteById(id);
	}

}
