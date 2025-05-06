package com.example.springLibrarySystem.service;

import com.example.springLibrarySystem.enums.EstadoLibro;
import com.example.springLibrarySystem.exception.LibroNoEncontradoException;
import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.repository.LibroRepository;
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
class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro(1L, "123-456-789", "Libro A", "Autor A", EstadoLibro.DISPONIBLE);
    }

    @Test
    void findByIsbnExistentReturnBook() {
        when(libroRepository.findByIsbn("123-456-789")).thenReturn(Optional.of(libro));

        Libro resultado = libroService.findByIsbn("123-456-789");

        assertNotNull(resultado);
        assertEquals("Libro A", resultado.getTitulo());
        verify(libroRepository).findByIsbn("123-456-789");
    }

    @Test
    void findByIsbnNotExistentReturnNull() {
        when(libroRepository.findByIsbn("000")).thenReturn(Optional.empty());

        assertThrows(LibroNoEncontradoException.class, () -> libroService.findByIsbn("000"));
        verify(libroRepository).findByIsbn("000");
    }

    @Test
    void findByIdExistenteReturnBook() {
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Libro resultado = libroService.findById(1L);

        assertEquals("Libro A", resultado.getTitulo());
        verify(libroRepository).findById(1L);
    }

    @Test
    void findByIdNotExistentReturnNull() {
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LibroNoEncontradoException.class, () -> libroService.findById(99L));
        verify(libroRepository).findById(99L);
    }

    @Test
    void findAllReturnList() {
        when(libroRepository.findAll()).thenReturn(List.of(libro));

        List<Libro> libros = libroService.findAll();

        assertEquals(1, libros.size());
        verify(libroRepository).findAll();
    }

    @Test
    void saveAndReturnBook() {
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro resultado = libroService.save(libro);

        assertEquals("Libro A", resultado.getTitulo());
        verify(libroRepository).save(libro);
    }

    @Test
    void deleteByIdDeleteBook() {
        doNothing().when(libroRepository).deleteById(1L);

        libroService.deleteById(1L);

        verify(libroRepository).deleteById(1L);
    }

    @Test
    void updateExistentUpdateAndReturnBook() {
        when(libroRepository.existsById(1L)).thenReturn(true);
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro resultado = libroService.update(1L, libro);

        assertEquals("Libro A", resultado.getTitulo());
        assertEquals(1L, resultado.getId());
        verify(libroRepository).save(libro);
    }

    @Test
    void updateNotExistentThrowException() {
        when(libroRepository.existsById(99L)).thenReturn(false);

        assertThrows(LibroNoEncontradoException.class, () -> libroService.update(99L, libro));
        verify(libroRepository, never()).save(any());
    }
}