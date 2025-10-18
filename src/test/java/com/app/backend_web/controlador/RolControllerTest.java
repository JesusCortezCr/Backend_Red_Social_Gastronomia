package com.app.backend_web.controlador;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.services.RolService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rol1;
    private Rol rol2;

    @BeforeEach
    void setUp() {
        rol1 = new Rol(1L, "ROLE_USER");
        rol2 = new Rol(2L, "ROLE_ADMIN");
    }

    @Test
    @DisplayName("GET /roles - Debería listar todos los roles y retornar 200 OK")
    void traerRoles_ShouldReturnListOfRoles() throws Exception {
        List<Rol> roles = Arrays.asList(rol1, rol2);
        given(rolService.listadoRoles()).willReturn(roles);

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("ROLE_USER"));
    }

    @Test
    @DisplayName("GET /roles/{id} - Debería encontrar un rol por ID y retornar 200 OK")
    void buscarRolporId_ShouldReturnRole_WhenFound() throws Exception {
        given(rolService.buscarRolPorId(1L)).willReturn(Optional.of(rol1));

        mockMvc.perform(get("/roles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("ROLE_USER"));
    }

    @Test
    @DisplayName("GET /roles/{id} - Debería retornar 404 Not Found si el rol no existe")
    void buscarRolporId_ShouldReturnNotFound_WhenNotExists() throws Exception {
        given(rolService.buscarRolPorId(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/roles/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}