package com.ecomerce.projeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.ecomerce.projeto.model.Categoria;
import com.github.javafaker.Faker;

@Service
public class CategoriaService {
	private static Faker faker = new Faker(new Locale("en-US"));
	private List<Categoria> lista = new ArrayList<>();
	
	public List<Categoria> getListaCategoriaFaker() {		
		for (int i = 0; i < 5; i++) {
			Categoria obj = new Categoria(faker.commerce().department());
			this.lista.add(obj);
		}
		
		return this.lista;
	}

}
