package com.example.springLibrarySystem.service;

import com.example.springLibrarySystem.models.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario findById(Long id);
    Usuario findByEmail(String email);
    Usuario findByNombre(String nombre);
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    Usuario update(Long id, Usuario usuario);
    void deleteById(Long id);
}
