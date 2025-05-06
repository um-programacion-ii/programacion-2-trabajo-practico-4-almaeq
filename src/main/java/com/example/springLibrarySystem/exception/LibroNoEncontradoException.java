package com.example.springLibrarySystem.exception;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException(Long id) {
        super("Libro no encontrado con ID: " + id);
    }

    public LibroNoEncontradoException(String isbn) {
        super("Libro no encontrado con ISBN: " + isbn);
    }
}

