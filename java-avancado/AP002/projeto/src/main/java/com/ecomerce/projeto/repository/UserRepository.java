package com.ecomerce.projeto.repository;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecomerce.projeto.model.Usuario;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcClient jdbcClient;

    @Transactional
    public Long saveUsuario(Usuario usuario) {
        var insertQuery = """
                INSERT INTO usuario(senha, email, role) 
                VALUES(?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(insertQuery)
                .param(1, usuario.getSenha())
                .param(2, usuario.getEmail())
                .param(3, "ROLE_USER")
                .update();
        return keyHolder.getKeyAs(Long.class);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        var findQuery = "SELECT id, senha, role, email FROM usuario WHERE email = :email";
        return jdbcClient.sql(findQuery).param("email", email).query(Usuario.class).optional();
    }
}
