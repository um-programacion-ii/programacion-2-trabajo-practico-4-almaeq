package com.example.springLibrarySystem.controller;

import com.example.springLibrarySystem.enums.EstadoLibro;
import com.example.springLibrarySystem.enums.EstadoUsuario;
import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.models.Prestamo;
import com.example.springLibrarySystem.models.Usuario;
import com.example.springLibrarySystem.service.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Prestamo createTrialLoan() {
        Libro libro = new Libro(1L, "123", "Libro de prueba", "Autor X", EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", EstadoUsuario.ACTIVO);
        return new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    void GETLoanReturnList() throws Exception {
        Prestamo p = createTrialLoan();
        when(prestamoService.findAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(p.getId()));
    }

    @Test
    void GETLoanByIdExistentReturnLoan() throws Exception {
        Prestamo p = createTrialLoan();
        when(prestamoService.findById(1L)).thenReturn(p);

        mockMvc.perform(get("/api/prestamos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libro.titulo").value("Libro de prueba"));
    }

    @Test
    void POSTCreateAndReturnLoan() throws Exception {
        Prestamo nuevo = createTrialLoan();
        nuevo.setId(null); // Simula entrada sin ID
        Prestamo creado = createTrialLoan();

        when(prestamoService.save(any())).thenReturn(creado);

        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(creado.getId()));
    }

    @Test
    void PUTUpdateLoan() throws Exception {
        Prestamo actualizado = createTrialLoan();
        when(prestamoService.update(eq(1L), any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/prestamos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.nombre").value("Juan"));
    }

    @Test
    void DELETELoan() throws Exception {
        doNothing().when(prestamoService).deleteById(1L);

        mockMvc.perform(delete("/api/prestamos/1"))
                .andExpect(status().isNoContent());

        verify(prestamoService).deleteById(1L);
    }
}
