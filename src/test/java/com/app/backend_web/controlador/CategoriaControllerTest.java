package com.app.backend_web.controlador;

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.services.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Categoria categoria1;
    private Categoria categoria2;

    @BeforeEach
    void setUp() {
        categoria1 = new Categoria(1L, "Postres");
        categoria2 = new Categoria(2L, "Platos Fuertes");
    }

    @Test
    @DisplayName("GET /categorias - Debería listar todas las categorías y retornar 200 OK")
    void traerCategorias_ShouldReturnListOfCategories() throws Exception {
        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);
        given(categoriaService.listadoCategorias()).willReturn(categorias);

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Postres"));
    }

    @Test
    @DisplayName("GET /categorias/{id} - Debería encontrar una categoría por ID y retornar 200 OK")
    void traerCategoriaPorId_ShouldReturnCategory_WhenFound() throws Exception {
        given(categoriaService.buscarCategoriaPorId(1L)).willReturn(Optional.of(categoria1));

        mockMvc.perform(get("/categorias/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Postres"));
    }

    @Test
    @DisplayName("GET /categorias/{id} - Debería retornar 404 Not Found si la categoría no existe")
    void traerCategoriaPorId_ShouldReturnNotFound_WhenNotExists() throws Exception {
        given(categoriaService.buscarCategoriaPorId(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/categorias/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /categorias/{id} - Debería eliminar una categoría y retornar 204 No Content")
    void eliminarCategoriaPorId_ShouldDeleteAndReturn204() throws Exception {
        doNothing().when(categoriaService).eliminarCategoria(1L);

        mockMvc.perform(delete("/categorias/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}