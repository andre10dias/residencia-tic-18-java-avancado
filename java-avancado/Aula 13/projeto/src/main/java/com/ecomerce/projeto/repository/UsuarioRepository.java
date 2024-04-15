package com.ecomerce.projeto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.projeto.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

//	List<Usuario> findByAtivoTrueOrAtivoNull();
	Page<Usuario> findByAtivoTrueOrAtivoNull(Pageable pageable);

}