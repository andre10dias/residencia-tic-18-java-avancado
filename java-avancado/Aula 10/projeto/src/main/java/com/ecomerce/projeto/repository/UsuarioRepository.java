package com.ecomerce.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.projeto.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}