package com.ecomerce.projeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Categoria;
import com.ecomerce.projeto.model.Produto;
import com.github.javafaker.Faker;

@Service
public class ProdutoService {
	private static Faker faker = new Faker(new Locale("en-US"));
	private List<Produto> lista = new ArrayList<>();
	
	public List<Produto> getListaProdutoFaker() {		
		for (int i = 0; i < 5; i++) {
			List<Categoria> categorias = new ArrayList<>();
			Categoria cat = new Categoria(faker.commerce().department());
			categorias.add(cat);
			
			String valor = faker.commerce().price().replace(",", ".");
			
			Produto obj = new Produto(null, faker.commerce().productName(), 
					Double.valueOf(valor), categorias);
			
			this.lista.add(obj);
		}
		
		return this.lista;
	}

}
