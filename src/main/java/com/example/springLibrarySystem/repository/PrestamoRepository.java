package com.example.springLibrarySystem.repository;

import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.models.Prestamo;
import com.example.springLibrarySystem.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    List<Prestamo> findAll();
    Optional<Prestamo> findByLibro(Libro libro);
    Optional<Prestamo> findByUsuario(Usuario usuario);
    void deleteById(Long id);
    boolean existsById(Long id);
}
