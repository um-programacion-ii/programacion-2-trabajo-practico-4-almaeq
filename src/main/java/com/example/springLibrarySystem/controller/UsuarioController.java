package com.example.springLibrarySystem.controller;

import com.example.springLibrarySystem.models.Usuario;
import com.example.springLibrarySystem.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene la lista completa de usuarios registrados.
     * @return Lista de usuarios
     */
    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id Identificador del usuario
     * @return Usuario correspondiente al ID
     */
    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtiene un usuario por su email.
     * @param email Correo electrónico del usuario
     * @return Usuario correspondiente al email
     */
    // GET /api/usuarios/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(usuarioService.findByEmail(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtiene un usuario por su nombre.
     * @param nombre Nombre del usuario
     * @return Usuario correspondiente al nombre
     */
    // GET /api/usuarios/nombre/{nombre}
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Usuario> getByNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(usuarioService.findByNombre(nombre));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Registra un nuevo usuario.
     * @param usuario Datos del usuario a registrar
     * @return Usuario registrado con ID asignado
     */
    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    /**
     * Actualiza un usuario existente.
     * @param id ID del usuario a actualizar
     * @param usuario Nuevos datos del usuario
     * @return Usuario actualizado
     */
    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(usuarioService.update(id, usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario
     * @return Sin contenido si se elimina correctamente
     */
    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
