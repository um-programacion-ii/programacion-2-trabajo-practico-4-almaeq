package com.example.springLibrarySystem.service;

import com.example.springLibrarySystem.models.Libro;

import java.util.List;

public interface LibroService {
    Libro findByIsbn(String isbn);
    List<Libro> findAll();
    Libro save(Libro libro);
    void deleteById(Long id);
    Libro update(Long id, Libro libro);
}
