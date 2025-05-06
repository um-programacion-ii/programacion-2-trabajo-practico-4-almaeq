package com.example.springLibrarySystem.service;

import com.example.springLibrarySystem.enums.EstadoLibro;
import com.example.springLibrarySystem.enums.EstadoUsuario;
import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.models.Prestamo;
import com.example.springLibrarySystem.models.Usuario;
import com.example.springLibrarySystem.repository.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    private Prestamo prestamo;
    private Libro libro;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        libro = new Libro(1L, "ABC", "Título", "Autor", EstadoLibro.DISPONIBLE);
        usuario = new Usuario(1L, "Carlos", "carlos@mail.com", EstadoUsuario.ACTIVO);
        prestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    void findByIdExistentReturnPrestamo() {
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getUsuario().getNombre());
        verify(prestamoRepository).findById(1L);
    }

    @Test
    void findByIdNotExistentThrowException() {
        when(prestamoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> prestamoService.findById(99L));
        verify(prestamoRepository).findById(99L);
    }

    @Test
    void findAllReturnList() {
        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.findAll();

        assertEquals(1, resultado.size());
        verify(prestamoRepository).findAll();
    }

    @Test
    void findByBookExistent() {
        when(prestamoRepository.findByLibro(libro)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.findByLibro(libro);

        assertNotNull(resultado);
        assertEquals(libro.getId(), resultado.getLibro().getId());
        verify(prestamoRepository).findByLibro(libro);
    }

    @Test
    void findByBookNotExistent() {
        when(prestamoRepository.findByLibro(libro)).thenReturn(Optional.empty());

        Prestamo resultado = prestamoService.findByLibro(libro);

        assertNull(resultado);
    }

    @Test
    void findByUserExistent() {
        when(prestamoRepository.findByUsuario(usuario)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.findByUsuario(usuario);

        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getUsuario().getId());
        verify(prestamoRepository).findByUsuario(usuario);
    }

    @Test
    void findByUserNotExistent() {
        when(prestamoRepository.findByUsuario(usuario)).thenReturn(Optional.empty());

        Prestamo resultado = prestamoService.findByUsuario(usuario);

        assertNull(resultado);
    }

    @Test
    void saveReturnLoanSaved() {
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);

        Prestamo resultado = prestamoService.save(prestamo);

        assertEquals(prestamo.getId(), resultado.getId());
        verify(prestamoRepository).save(prestamo);
    }

    @Test
    void updateExistentUpdateAndReturnLoan() {
        when(prestamoRepository.existsById(1L)).thenReturn(true);
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);

        Prestamo resultado = prestamoService.update(1L, prestamo);

        assertEquals(prestamo.getId(), resultado.getId());
        verify(prestamoRepository).save(prestamo);
    }

    @Test
    void updateNotExistentThrowException() {
        when(prestamoRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> prestamoService.update(99L, prestamo));
        verify(prestamoRepository, never()).save(any());
    }

    @Test
    void deleteByIdCallsRepository() {
        doNothing().when(prestamoRepository).deleteById(1L);

        prestamoService.deleteById(1L);

        verify(prestamoRepository).deleteById(1L);
    }
}
