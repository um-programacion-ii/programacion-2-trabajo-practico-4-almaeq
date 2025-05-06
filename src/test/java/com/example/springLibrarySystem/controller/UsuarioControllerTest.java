package com.example.springLibrarySystem.controller;

import com.example.springLibrarySystem.enums.EstadoUsuario;
import com.example.springLibrarySystem.models.Usuario;
import com.example.springLibrarySystem.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario createUser() {
        return new Usuario(1L, "Juan", "juan@mail.com", EstadoUsuario.ACTIVO);
    }

    @Test
    void GETUsersReturnList() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(createUser()));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void GETUserByIdExistentReturnUser() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(createUser());

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@mail.com"));
    }

    @Test
    void GETUserByIdNotExistentReturn404() throws Exception {
        when(usuarioService.findById(999L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void POSTUSerCreateAndReturnUser() throws Exception {
        Usuario nuevo = new Usuario(null, "Ana", "ana@mail.com", EstadoUsuario.ACTIVO);
        Usuario creado = new Usuario(2L, "Ana", "ana@mail.com", EstadoUsuario.ACTIVO);

        when(usuarioService.save(any())).thenReturn(creado);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    void PUTUSerUpdateUser() throws Exception {
        Usuario actualizado = createUser();
        actualizado.setNombre("Juan Carlos");

        when(usuarioService.update(eq(1L), any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Carlos"));
    }

    @Test
    void PUTUserNotExistentReturn404() throws Exception {
        Usuario usuario = createUser();

        when(usuarioService.update(eq(999L), any())).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(put("/api/usuarios/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    void DELETEUser() throws Exception {
        doNothing().when(usuarioService).deleteById(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService).deleteById(1L);
    }

    @Test
    void DELETEUserNotExistentReturn404() throws Exception {
        doThrow(new RuntimeException("Usuario no encontrado"))
                .when(usuarioService).deleteById(999L);

        mockMvc.perform(delete("/api/usuarios/999"))
                .andExpect(status().isNotFound());
    }
}
