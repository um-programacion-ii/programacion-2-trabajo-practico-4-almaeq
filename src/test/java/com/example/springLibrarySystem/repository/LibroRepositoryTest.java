package com.example.springLibrarySystem.repository;

import com.example.springLibrarySystem.enums.EstadoLibro;
import com.example.springLibrarySystem.models.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LibroRepositoryTest {
    private LibroRepositoryImpl libroRepository;

    @BeforeEach
    void setUp() {
        libroRepository = new LibroRepositoryImpl();
    }

    @Test
    void saveBookAsignIdToBook() {
        Libro libro = new Libro(null, "111-222", "Libro A", "Autor A", EstadoLibro.DISPONIBLE);
        Libro guardado = libroRepository.save(libro);

        assertNotNull(guardado.getId());
        assertEquals("Libro A", guardado.getTitulo());
    }

    @Test
    void findByIdExistentReturnBook() {
        Libro libro = libroRepository.save(new Libro(null, "999-888", "Libro B", "Autor B", EstadoLibro.DISPONIBLE));
        Optional<Libro> resultado = libroRepository.findById(libro.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Libro B", resultado.get().getTitulo());
    }

    @Test
    void findByIdInexistentReturnEmpty() {
        Optional<Libro> resultado = libroRepository.findById(999L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void findByIsbnExistentReturnBook() {
        libroRepository.save(new Libro(null, "ABC-123", "Libro C", "Autor C", EstadoLibro.DISPONIBLE));
        Optional<Libro> resultado = libroRepository.findByIsbn("ABC-123");

        assertTrue(resultado.isPresent());
        assertEquals("Libro C", resultado.get().getTitulo());
    }

    @Test
    void findByIsbnInexistentReturnEmpty() {
        Optional<Libro> resultado = libroRepository.findByIsbn("XYZ-000");
        assertFalse(resultado.isPresent());
    }


    @Test
    void findAllReturnAllBooks() {
        libroRepository.save(new Libro(null, "1", "Libro 1", "Autor", EstadoLibro.DISPONIBLE));
        libroRepository.save(new Libro(null, "2", "Libro 2", "Autor", EstadoLibro.DISPONIBLE));

        List<Libro> todos = libroRepository.findAll();
        assertEquals(2, todos.size());
    }

    @Test
    void deleteByIdDeleteBook() {
        Libro libro = libroRepository.save(new Libro(null, "DEL-001", "Libro D", "Autor D", EstadoLibro.DISPONIBLE));
        libroRepository.deleteById(libro.getId());

        assertFalse(libroRepository.findById(libro.getId()).isPresent());
    }

    @Test
    void existsByIdReturnTrueIfBookExists() {
        Libro libro = libroRepository.save(new Libro(null, "EX-001", "Libro E", "Autor E", EstadoLibro.DISPONIBLE));
        assertTrue(libroRepository.existsById(libro.getId()));
    }

    @Test
    void existsByIdReturnFalseIfBookDoesNotExist() {
        assertFalse(libroRepository.existsById(1234L));
    }
}
