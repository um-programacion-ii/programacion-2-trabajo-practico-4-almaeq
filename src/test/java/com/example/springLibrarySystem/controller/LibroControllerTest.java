package com.example.springLibrarySystem.controller;

import com.example.springLibrarySystem.enums.EstadoLibro;
import com.example.springLibrarySystem.exception.LibroNoEncontradoException;
import com.example.springLibrarySystem.models.Libro;
import com.example.springLibrarySystem.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void GETBooksReturnList() throws Exception {
        List<Libro> libros = List.of(
                new Libro(1L, "123", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE),
                new Libro(2L, "456", "Libro 2", "Autor 2", EstadoLibro.PRESTADO)
        );
        when(libroService.findAll()).thenReturn(libros);

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Libro 1"))
                .andExpect(jsonPath("$[1].estado").value("PRESTADO"));
    }

    @Test
    void GETBookByIdExistentReturnBook() throws Exception {
        // Arrange
        Libro libro = new Libro(1L, "123", "Libro A", "Autor A", EstadoLibro.DISPONIBLE);
        when(libroService.findById(1L)).thenReturn(libro);

        // Act & Assert
        mockMvc.perform(get("/api/libros/1"))  // CORREGIDO: sin "L"
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Libro A"))
                .andExpect(jsonPath("$.estado").value("DISPONIBLE"));
    }

    @Test
    void GETBookByIdNotExistentReturn404() throws Exception {
        when(libroService.findByIsbn("xxx")).thenThrow(new LibroNoEncontradoException("xxx"));

        mockMvc.perform(get("/api/libros/isbn/xxx"))
                .andExpect(status().isNotFound());
    }

    @Test
    void GETBookByIsbnExistentReturnBook() throws Exception {
        // Arrange
        String isbn = "123-456";
        Libro libro = new Libro(1L, isbn, "Libro A", "Autor A", EstadoLibro.DISPONIBLE);
        when(libroService.findByIsbn(isbn)).thenReturn(libro);

        // Act & Assert
        mockMvc.perform(get("/api/libros/isbn/" + isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.titulo").value("Libro A"))
                .andExpect(jsonPath("$.estado").value("DISPONIBLE"));
    }

    @Test
    void GETBookByIsbnNoExistentReturn404() throws Exception {
        // Arrange
        String isbn = "inexistente";
        when(libroService.findByIsbn(isbn)).thenThrow(new LibroNoEncontradoException(isbn));

        // Act & Assert
        mockMvc.perform(get("/api/libros/isbn/" + isbn))
                .andExpect(status().isNotFound());
    }

    @Test
    void POSTBookCreateAndReturnBook() throws Exception {
        Libro nuevo = new Libro(null, "789", "Nuevo Libro", "Autor", EstadoLibro.DISPONIBLE);
        Libro creado = new Libro(3L, "789", "Nuevo Libro", "Autor", EstadoLibro.DISPONIBLE);

        when(libroService.save(any())).thenReturn(creado);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void PUTBookUpdateBook() throws Exception {
        Libro actualizado = new Libro(1L, "123", "Actualizado", "Autor", EstadoLibro.DISPONIBLE);
        when(libroService.update(eq(1L), any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Actualizado"));
    }

    @Test
    void DELETEBook() throws Exception {
        doNothing().when(libroService).deleteById(1L);

        mockMvc.perform(delete("/api/libros/1"))
                .andExpect(status().isNoContent());

        verify(libroService).deleteById(1L);
    }
}
