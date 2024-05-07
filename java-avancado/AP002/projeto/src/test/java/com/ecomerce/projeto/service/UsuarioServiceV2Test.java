package com.ecomerce.projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.ecomerce.projeto.controller.dto.UsuarioDTO;
import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UsuarioRepository;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceV2Test {
	@Mock
	private UsuarioRepository repository;
	
	@InjectMocks
	private UsuarioServiceV2 service;
	
	private Faker faker;
	
	@BeforeEach
	void setUp() {
		faker = new Faker();
	}
		
	private Usuario usuarioFakeFaker() {
		Usuario usuarioFake = new Usuario();
		usuarioFake.setId(faker.number().randomNumber());
		usuarioFake.setEmail(faker.internet().emailAddress());
		usuarioFake.setSenha(faker.number().digits(8));
		
		return usuarioFake;
	}
	
	@Test
	void createUsuario_WithValidData_ReturnUsuario() {
		Usuario usuarioFakeFake = usuarioFakeFaker();
		given(repository.save(any(Usuario.class))).willReturn(usuarioFakeFake);
		
		Usuario savedUsuario = service.cadastrar(usuarioFakeFake);
		
		assertNotNull(usuarioFakeFake);
		assertEquals(usuarioFakeFake.getEmail(), savedUsuario.getEmail());
		assertEquals(usuarioFakeFake.getSenha(), savedUsuario.getSenha());
		verify(repository).save(any(Usuario.class));
		
	}
	
	@Test
	void createUsuario_WithLoginAlreadyExists_ThrowsDataIntegrityViolationException(){
		
		Usuario usuarioFakeFake = usuarioFakeFaker();
		
		// Simulating a uniqueness restrict violation of the "login" field.
		willThrow(DataIntegrityViolationException.class).given(repository).save(argThat(newUsuario -> newUsuario.getEmail().equals(usuarioFakeFake.getEmail())));
		
		//Attempts to create the Employee, expecting the exception to be thrown due to the name uniqueness violation.
		assertThrows(DataIntegrityViolationException.class, () -> service.cadastrar(usuarioFakeFake));
		
		// Checks whether the save method was actually called, indicating that the attempt to save the Usuario was made.
        then(repository).should().save(any(Usuario.class));
		
	}

	@Test
	void deleteUsuario_WithValidId_DeletesUsuario() {
	    // Create a fake user
	    Usuario usuarioFakeFake = usuarioFakeFaker();

	    // Call the delete method of service
	    service.remover(usuarioFakeFake.getId());

	    // Verify that the repository.delete method was called with the fake user
	    verify(repository).deleteById(usuarioFakeFake.getId());
	}
	
	@Test
    void findUsuarioById_WithUnexistingId_ReturnsEmpty() {
        Long fakeId = faker.number().randomNumber();
        given(repository.findById(fakeId)).willReturn(Optional.empty());

        Optional<Usuario> result = service.pegarPorId(fakeId);

        assertFalse(result.isPresent());
        verify(repository).findById(fakeId);
	}
      
    @Test
    void updateUsuario_WithValidData_ReturnsUpdatedUsuario() {
        Usuario originalUsuario = usuarioFakeFaker();
        Usuario updatedUsuario = usuarioFakeFaker();

        given(repository.findById(originalUsuario.getId())).willReturn(Optional.of(originalUsuario));
        given(repository.save(any(Usuario.class))).willReturn(updatedUsuario);

        Optional<Usuario> result = service.atualizar(originalUsuario.getId(), updatedUsuario);

        assertTrue(result.isPresent());
        assertEquals(updatedUsuario.getEmail(), result.get().getEmail());
        assertEquals(updatedUsuario.getSenha(), result.get().getSenha());
        verify(repository).findById(originalUsuario.getId());
        verify(repository).save(any(Usuario.class));
    }
    
    @Test
    void removerLogicamente_WithValidId_ReturnsDeactivatedUsuario() {
        // Criar um usuário fake
        Usuario usuarioFake = usuarioFakeFaker();
        usuarioFake.setAtivo(true); // Garantir que o usuário está ativo

        // Simular a busca do usuário pelo id
        given(repository.findById(usuarioFake.getId())).willReturn(Optional.of(usuarioFake));

        // Simular o salvamento do usuário após a remoção lógica
        given(repository.save(any(Usuario.class))).willAnswer(invocation -> invocation.getArgument(0));

        // Chamar o método de remoção lógica
        Optional<Usuario> result = service.removerLogicamente(usuarioFake.getId());

        // Verificar se o resultado está presente
        assertTrue(result.isPresent());

        // Verificar se o usuário retornado não está mais ativo
        assertFalse(result.get().getAtivo());

        // Verificar se o método findById foi chamado com o id correto
        verify(repository).findById(usuarioFake.getId());

        // Verificar se o método save foi chamado para salvar o usuário com o status de ativo atualizado
        verify(repository).save(any(Usuario.class));
    }


}
