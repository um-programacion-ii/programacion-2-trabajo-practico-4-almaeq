package com.example.springLibrarySystem.controller;

import com.example.springLibrarySystem.models.Prestamo;
import com.example.springLibrarySystem.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    /**
     * Obtiene la lista completa de prestamos registrados.
     * @return Lista de prestamos
     */
    // GET /api/prestamos
    @GetMapping
    public ResponseEntity<List<Prestamo>> getAll() {
        return ResponseEntity.ok(prestamoService.findAll());
    }

    /**
     * Obtiene un prestamo por su ID.
     * @param id Identificador del préstamo
     * @return Prestamo correspondiente al ID
     */
    // GET /api/prestamos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getById(@PathVariable Long id) {
        try {
            Prestamo prestamo = prestamoService.findById(id);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Registra un nuevo préstamo.
     * @param prestamo Datos del prestamo a registrar
     * @return Prestamo registrado con ID asignado
     */
    // POST /api/prestamos
    @PostMapping
    public ResponseEntity<Prestamo> create(@RequestBody Prestamo prestamo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.save(prestamo));
    }

    /**
     * Actualiza un prestamo existente.
     * @param id ID del libro a actualizar
     * @param prestamo Nuevos datos del libro
     * @return prestamo actualizado
     */
    // PUT /api/prestamos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> update(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        try {
            Prestamo actualizado = prestamoService.update(id, prestamo);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un prestamo por su ID.
     * @param id ID del prestamo
     * @return Sin contenido si se elimina correctamente
     */
    // DELETE /api/prestamos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            prestamoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
