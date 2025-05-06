package com.example.springLibrarySystem.service;

import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.models.Prestamo;
import com.example.springLibrarySystem.models.Usuario;
import com.example.springLibrarySystem.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public Prestamo findById(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
    }

    @Override
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo findByLibro(Libro libro) {
        return prestamoRepository.findByLibro(libro).orElse(null);
    }

    @Override
    public Prestamo findByUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario).orElse(null);
    }

    @Override
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo update(Long id, Prestamo prestamo) {
        if (!prestamoRepository.existsById(id)) {
            throw new RuntimeException("Préstamo no encontrado con ID: " + id);
        }
        prestamo.setId(id);
        return prestamoRepository.save(prestamo);
    }

    @Override
    public void deleteById(Long id) {
        prestamoRepository.deleteById(id);
    }
}
