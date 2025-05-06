package com.example.springLibrarySystem.repository;

import com.example.springLibrarySystem.enums.EstadoLibro;
import com.example.springLibrarySystem.enums.EstadoUsuario;
import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.models.Prestamo;
import com.example.springLibrarySystem.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoRepositoryTest {

    private PrestamoRepositoryImpl prestamoRepository;

    private Libro libro;
    private Usuario usuario;
    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        prestamoRepository = new PrestamoRepositoryImpl();

        libro = new Libro(1L, "111-222", "Libro Test", "Autor", EstadoLibro.DISPONIBLE);
        usuario = new Usuario(1L, "Juan", "juan@mail.com", EstadoUsuario.ACTIVO);
        prestamo = new Prestamo(null, libro, usuario, LocalDate.now(), null);
    }

    @Test
    void saveLoanAsignIdToLoan() {
        Prestamo guardado = prestamoRepository.save(prestamo);

        assertNotNull(guardado.getId());
        assertEquals(libro, guardado.getLibro());
        assertEquals(usuario, guardado.getUsuario());
    }

    @Test
    void findByIdExistentReturnLoan() {
        Prestamo guardado = prestamoRepository.save(prestamo);
        Optional<Prestamo> resultado = prestamoRepository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals(libro, resultado.get().getLibro());
    }

    @Test
    void findByIdInexistentReturnEmpty() {
        Optional<Prestamo> resultado = prestamoRepository.findById(999L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void findByBookExistentReturnLoan() {
        prestamoRepository.save(prestamo);
        Optional<Prestamo> resultado = prestamoRepository.findByLibro(libro);

        assertTrue(resultado.isPresent());
        assertEquals(libro, resultado.get().getLibro());
    }

    @Test
    void findByBookInexistentReturnEmpty() {
        Libro otroLibro = new Libro(2L, "222-333", "Otro Libro", "Autor", EstadoLibro.DISPONIBLE);
        Optional<Prestamo> resultado = prestamoRepository.findByLibro(otroLibro);

        assertFalse(resultado.isPresent());
    }

    @Test
    void findByUserExistentReturnLoan() {
        prestamoRepository.save(prestamo);
        Optional<Prestamo> resultado = prestamoRepository.findByUsuario(usuario);

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get().getUsuario());
    }

    @Test
    void findByUserInexistentReturnEmpty() {
        Usuario otroUsuario = new Usuario(2L, "Maria", "maria@mail.com", EstadoUsuario.ACTIVO);
        Optional<Prestamo> resultado = prestamoRepository.findByUsuario(otroUsuario);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deleteByIdDeleteLoan() {
        Prestamo guardado = prestamoRepository.save(prestamo);
        prestamoRepository.deleteById(guardado.getId());

        assertFalse(prestamoRepository.findById(guardado.getId()).isPresent());
    }

    @Test
    void existsByIdReturnTrueIfLoanExists() {
        Prestamo guardado = prestamoRepository.save(prestamo);
        assertTrue(prestamoRepository.existsById(guardado.getId()));
    }

    @Test
    void existsByIdReturnFalseIfLoanDoesNotExist() {
        assertFalse(prestamoRepository.existsById(123L));
    }

    @Test
    void findAllReturnAllLoans() {
        prestamoRepository.save(prestamo);
        prestamoRepository.save(new Prestamo(null,
                new Libro(2L, "444-555", "Otro Libro", "Autor", EstadoLibro.DISPONIBLE),
                new Usuario(2L, "Ana", "ana@mail.com", EstadoUsuario.ACTIVO),
                LocalDate.now(), null
        ));

        List<Prestamo> lista = prestamoRepository.findAll();
        assertEquals(2, lista.size());
    }
}
