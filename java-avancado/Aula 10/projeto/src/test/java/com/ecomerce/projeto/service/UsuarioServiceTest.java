package com.ecomerce.projeto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import com.ecomerce.projeto.model.Usuario;
import com.ecomerce.projeto.repository.UsuarioRepository;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	@Mock
	private UsuarioRepository repository;
	
	@InjectMocks
	private UsuarioService service;
	
	private Faker faker;
	
	@BeforeEach
	void setUp() {
		faker = new Faker();
	}
		
	private Usuario usuarioFaker() {
		Usuario usuario = new Usuario();
		usuario.setId(faker.number().randomNumber());
		usuario.setEmail(faker.internet().emailAddress());
		usuario.setSenha(faker.number().digits(8));
		
		return usuario;
	}
	
	@Test
	void createUsuario_WithValidData_ReturnUsuario() {
		Usuario usuarioFake = usuarioFaker();
		given(repository.save(any(Usuario.class))).willReturn(usuarioFake);
		
		Usuario savedUsuario = service.cadastrar(usuarioFake);
		
		assertNotNull(usuarioFake);
		assertEquals(usuarioFake.getEmail(), savedUsuario.getEmail());
		assertEquals(usuarioFake.getSenha(), savedUsuario.getSenha());
		verify(repository).save(any(Usuario.class));
		
	}
	
	@Test
	void createUsuario_WithLoginAlreadyExists_ThrowsDataIntegrityViolationException(){
		
		Usuario usuarioFake = usuarioFaker();
		
		// Simulating a uniqueness restrict violation of the "login" field.
		willThrow(DataIntegrityViolationException.class).given(repository).save(argThat(newUsuario -> newUsuario.getEmail().equals(usuarioFake.getEmail())));
		
		//Attempts to create the Employee, expecting the exception to be thrown due to the name uniqueness violation.
		assertThrows(DataIntegrityViolationException.class, () -> service.cadastrar(usuarioFake));
		
		// Checks whether the save method was actually called, indicating that the attempt to save the Usuario was made.
        then(repository).should().save(any(Usuario.class));
		
	}
	

	@Test
	void deleteUsuario_WithValidId_DeletesUsuario() {
	    // Create a fake user
	    Usuario usuarioFake = usuarioFaker();

	    // Call the delete method of service
	    service.remover(usuarioFake.getId());

	    // Verify that the repository.delete method was called with the fake user
	    verify(repository).deleteById(usuarioFake.getId());
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
	        Usuario originalUsuario = usuarioFaker();
	        Usuario updatedUsuario = usuarioFaker();

	        given(repository.findById(originalUsuario.getId())).willReturn(Optional.of(originalUsuario));
	        given(repository.save(any(Usuario.class))).willReturn(updatedUsuario);

	        Optional<Usuario> result = service.atualizar(originalUsuario.getId(), updatedUsuario);

	        assertTrue(result.isPresent());
	        assertEquals(updatedUsuario.getEmail(), result.get().getEmail());
	        assertEquals(updatedUsuario.getSenha(), result.get().getSenha());
	        verify(repository).findById(originalUsuario.getId());
	        verify(repository).save(any(Usuario.class));
	    }

}
