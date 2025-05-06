package com.example.springLibrarySystem.models;

import com.example.springLibrarySystem.enums.EstadoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private EstadoUsuario estado;
}
