package com.ecomerce.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecomerce.projeto.model.Produto;
import com.ecomerce.projeto.repository.ProdutoRepository;
import com.github.javafaker.Faker;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
//	private static Faker faker = new Faker(new Locale("en-US"));
//	private List<Produto> lista = new ArrayList<>();
//	
//	public List<Produto> getListaProdutoFaker() {		
//		for (int i = 0; i < 5; i++) {
//			List<Categoria> categorias = new ArrayList<>();
//			
//			Categoria categoria = new Categoria(faker.commerce().material());
//			categorias.add(categoria);
//			
//			String valor = faker.commerce().price().replace(",", ".");
//			
//			Produto obj = new Produto(null, faker.commerce().productName(), 
//					Double.valueOf(valor), categorias);
//			
//			this.lista.add(obj);
//		}
//		
//		return this.lista;
//	}
	
	@Cacheable("produtos")
	public Page<Produto> listarProdutos(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Produto cadastrar(Produto u) {
		return repository.save(u);
	}
	
	public Optional<Produto> pegarPorId(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Produto> atualizar(Long id, Produto u) {
		return repository.findById(id).map(produto -> {
			produto.setNome(u.getNome());
			produto.setPreco(u.getPreco());
            return repository.save(produto);
        });
	}
	
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
//	public List<Produto> pegarProdutosPorCategoria(@PathVariable Long categoriaId) {
//		List<Produto> listaProdutos = repository.findProdutosByCategoriaId(categoriaId);
//		return listaProdutos;
//	}

}
