package com.recupera.senha.recuperaSenha.usuarioRepository;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.recupera.senha.recuperaSenha.model.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UsuarioRepository {
	
private final JdbcClient jdbcClient;
	
	@Transactional
	public Long saveUsuario(Usuario usuario) {
		var query = "INSERT INTO usuario (email, username, password, role, token) VALUES (?, ?, ?, ?, ?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcClient.sql(query)
		.param(1, usuario.getEmail())
		.param(2, usuario.getUsername())
		.param(3, usuario.getPassword())
		.param(4, usuario.getRole())
		.param(5, usuario.getToken())
		.update();
		
		return keyHolder.getKeyAs(Long.class);
	}
			
	@Transactional(readOnly = true)
	public Optional<Usuario> findByUsername(String username) {
		var findQuery = "SELECT * FROM usuario WHERE username=:username";
		return jdbcClient.sql(findQuery)
				.param("username", username)
				.query(Usuario.class).optional();
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> findByEmail(String email) {
		var findQuery = "SELECT * FROM usuario WHERE email=:email";
		return jdbcClient.sql(findQuery)
				.param("email", email)
				.query(Usuario.class).optional();
	}
	
	@Transactional
	public void updateToken(String token, Long id) {
			log.info("update Token: " + token);
			log.info("update token id: " + id);
			var query = "UPDATE usuario SET token=:token WHERE id=:_id";
			jdbcClient.sql(query)
			.param("token", token)
			.param("_id", id)
			.update();
	}
	
	@Transactional
	public Optional<Usuario> findByToken(String token) {
		var findQuery = "SELECT * FROM usuario WHERE token=:token";
		return jdbcClient.sql(findQuery)
				.param("token", token)
				.query(Usuario.class).optional();
	}
	
	@Transactional
	public void updatePassword(String password, Long id) {
		var query = "UPDATE usuario SET password=:password WHERE id=:id";
		jdbcClient.sql(query)
		.param("password", password)
		.param("id", id)
		.update();
	}

}
