package com.ecomerce.projeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Categoria;
import com.ecomerce.projeto.repository.CategoriaRepository;
import com.github.javafaker.Faker;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
//	private static Faker faker = new Faker(new Locale("en-US"));
//	private List<Categoria> lista = new ArrayList<>();
//	
//	public List<Categoria> getListaCategoriaFaker() {		
//		for (int i = 0; i < 5; i++) {
//			Categoria obj = new Categoria(faker.commerce().department());
//			this.lista.add(obj);
//		}
//		
//		return this.lista;
//	}
	
	@Cacheable("categorias")
	public Page<Categoria> listarCategorias(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Categoria cadastrar(Categoria u) {
		return repository.save(u);
	}
	
	public Optional<Categoria> pegarPorId(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Categoria> atualizar(Long id, Categoria u) {
		return repository.findById(id).map(categoria -> {
			categoria.setNome(u.getNome());
			categoria.setProdutos(u.getProdutos());
            return repository.save(categoria);
        });
	}
	
	public void remover(Long id) {
		repository.deleteById(id);
	}

}
