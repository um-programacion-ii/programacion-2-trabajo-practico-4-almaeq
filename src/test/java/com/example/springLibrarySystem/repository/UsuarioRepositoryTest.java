package com.example.springLibrarySystem.repository;

import com.example.springLibrarySystem.enums.EstadoUsuario;
import com.example.springLibrarySystem.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositoryTest {

    private UsuarioRepositoryImpl usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository = new UsuarioRepositoryImpl();
    }

    @Test
    void saveUserAsignIdToUser() {
            Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", EstadoUsuario.ACTIVO);
        Usuario guardado = usuarioRepository.save(usuario);

        assertNotNull(guardado.getId());
        assertEquals("Juan", guardado.getNombre());
        assertEquals("juan@mail.com", guardado.getEmail());
    }

    @Test
    void findByIdExistentReturnUser() {
        Usuario guardado = usuarioRepository.save(new Usuario(1L, "Ana", "ana@mail.com", EstadoUsuario.ACTIVO));
        Optional<Usuario> resultado = usuarioRepository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    void findByIdInexistentReturnEmpty() {
        Optional<Usuario> resultado = usuarioRepository.findById(1234L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void findByEmailExistentReturnUser() {
        usuarioRepository.save(new Usuario(1L, "Luis", "luis@mail.com", EstadoUsuario.ACTIVO));
        Optional<Usuario> resultado = usuarioRepository.findByEmail("luis@mail.com");

        assertTrue(resultado.isPresent());
        assertEquals("Luis", resultado.get().getNombre());
    }

    @Test
    void findByEmailInexistentReturnEmpty() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("noexiste@mail.com");
        assertFalse(resultado.isPresent());
    }

    @Test
    void findByNameExistentReturnUser() {
        usuarioRepository.save(new Usuario(1L, "Carlos", "carlos@mail.com", EstadoUsuario.ACTIVO));
        Optional<Usuario> resultado = usuarioRepository.findByNombre("Carlos");

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
    }

    @Test
    void findByNameInexistentReturnEmpty() {
        Optional<Usuario> resultado = usuarioRepository.findByNombre("Desconocido");
        assertFalse(resultado.isPresent());
    }

    @Test
    void deleteByIdDeleteUser() {
        Usuario guardado = usuarioRepository.save(new Usuario(1L, "Pepe", "pepe@mail.com", EstadoUsuario.ACTIVO));
        usuarioRepository.deleteById(guardado.getId());

        assertFalse(usuarioRepository.findById(guardado.getId()).isPresent());
    }

    @Test
    void existsByIdReturnTrueIfUserExists() {
        Usuario guardado = usuarioRepository.save(new Usuario(1L, "Valeria", "valeria@mail.com", EstadoUsuario.ACTIVO));
        assertTrue(usuarioRepository.existsById(guardado.getId()));
    }

    @Test
    void existsByIdReturnFalseIfUserDoesNotExist() {
        assertFalse(usuarioRepository.existsById(999L));
    }

    @Test
    void findAllReturnAllUsers() {
        usuarioRepository.save(new Usuario(1L, "A", "a@mail.com", EstadoUsuario.ACTIVO));
        usuarioRepository.save(new Usuario(2L, "B", "b@mail.com", EstadoUsuario.INACTIVO));

        List<Usuario> usuarios = usuarioRepository.findAll();
        assertEquals(2, usuarios.size());
    }
}
