package com.example.springLibrarySystem.service;

import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.models.Prestamo;
import com.example.springLibrarySystem.models.Usuario;

import java.util.List;

public interface PrestamoService {
    Prestamo findById(Long id);
    List<Prestamo> findAll();
    Prestamo findByLibro(Libro libro);
    Prestamo findByUsuario(Usuario usuario);
    Prestamo save(Prestamo prestamo);
    Prestamo update(Long id, Prestamo prestamo);
    void deleteById(Long id);
}
