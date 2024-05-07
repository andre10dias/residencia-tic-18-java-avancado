package com.ecomerce.projeto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.service.UsuarioServiceV2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v2/usuarios")
public class UsuarioControllerV2 {
	
	@Autowired
	UsuarioServiceV2 service;
	
	@GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = service.listarUsuarios();
        log.info("[UsuarioController]: listarUsuarios");
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = service.pegarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario createdUsuario = service.cadastrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario updatedUsuario) {
        Optional<Usuario> usuario = service.atualizar(id, updatedUsuario);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
//        service.remover(id);
//        return ResponseEntity.noContent().build();
//    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioLogic(@PathVariable Long id) {
        service.removerLogicamente(id);
        return ResponseEntity.noContent().build();
    }

}
