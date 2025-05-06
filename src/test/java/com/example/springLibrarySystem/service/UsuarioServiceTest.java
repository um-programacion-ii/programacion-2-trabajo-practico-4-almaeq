package com.example.springLibrarySystem.service;

import com.example.springLibrarySystem.enums.EstadoUsuario;
import com.example.springLibrarySystem.models.Usuario;
import com.example.springLibrarySystem.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Sofía", "sofia@mail.com", EstadoUsuario.ACTIVO);
    }

    @Test
    void findByIdExistentReturnUser() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Sofía", resultado.getNombre());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void findByIdNotExistentThrowException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.findById(99L));
        verify(usuarioRepository).findById(99L);
    }

    @Test
    void findByEmailExistentReturnUser() {
        when(usuarioRepository.findByEmail("sofia@mail.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findByEmail("sofia@mail.com");

        assertEquals(1L, resultado.getId());
        verify(usuarioRepository).findByEmail("sofia@mail.com");
    }

    @Test
    void findByEmailNotExistentThrowException() {
        when(usuarioRepository.findByEmail("x@mail.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.findByEmail("x@mail.com"));
    }

    @Test
    void findByNameExistentReturnUser() {
        when(usuarioRepository.findByNombre("Sofía")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findByNombre("Sofía");

        assertEquals("sofia@mail.com", resultado.getEmail());
        verify(usuarioRepository).findByNombre("Sofía");
    }

    @Test
    void findByNameNotExistentThrowException() {
        when(usuarioRepository.findByNombre("Pedro")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.findByNombre("Pedro"));
    }

    @Test
    void findAllReturnList() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.findAll();

        assertEquals(1, usuarios.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void saveReturnUserSaved() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.save(usuario);

        assertEquals("Sofía", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void updateExistentUser() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.update(1L, usuario);

        assertEquals(1L, resultado.getId());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void updateNotExistentThrowException() {
        when(usuarioRepository.existsById(999L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> usuarioService.update(999L, usuario));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deleteByIdCallsRepository() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.deleteById(1L);

        verify(usuarioRepository).deleteById(1L);
    }
}
