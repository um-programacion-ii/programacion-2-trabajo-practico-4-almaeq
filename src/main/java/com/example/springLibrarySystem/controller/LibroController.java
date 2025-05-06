package com.example.springLibrarySystem.controller;

import com.example.springLibrarySystem.exception.LibroNoEncontradoException;
import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    /**
     * Obtiene la lista completa de libros registrados.
     * @return Lista de libros
     */
    // GET /api/libros
    @GetMapping
    public ResponseEntity<List<Libro>> getAll() {
        return ResponseEntity.ok(libroService.findAll());
    }

    /**
     * Obtiene un libro por su ID.
     * @param id Identificador del libro
     * @return Libro correspondiente al ID
     */
    // GET /api/libros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getById(@PathVariable Long id) {
        try {
            Libro libro = libroService.findById(id);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtiene un libro por su ISBN.
     * @param isbn Código ISBN
     * @return Libro correspondiente al ISBN
     */
    // GET /api/libros/isbn/{isbn}
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> getByIsbn(@PathVariable String isbn) {
        try {
            Libro libro = libroService.findByIsbn(isbn);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Registra un nuevo libro.
     * @param libro Datos del libro a registrar
     * @return Libro registrado con ID asignado
     */
    // POST /api/libros
    @PostMapping
    public ResponseEntity<Libro> create(@RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.save(libro));
    }

    /**
     * Actualiza un libro existente.
     * @param id ID del libro a actualizar
     * @param libro Nuevos datos del libro
     * @return Libro actualizado
     */
    // PUT /api/libros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            return ResponseEntity.ok(libroService.update(id, libro));
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un libro por su ID.
     * @param id ID del libro
     * @return Sin contenido si se elimina correctamente
     */
    // DELETE /api/libros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            libroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
