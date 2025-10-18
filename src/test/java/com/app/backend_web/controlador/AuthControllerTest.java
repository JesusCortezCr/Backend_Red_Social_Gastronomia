package com.app.backend_web.controlador;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.configuration.JwtUtils;
import com.app.backend_web.controllers.AuthController;
import com.app.backend_web.repositories.RolRepository;
import com.app.backend_web.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario nuevoUsuario;
    private Rol rolUsuario;

    @BeforeEach
    void setUp() {
        rolUsuario = new Rol();
        rolUsuario.setId(2L);
        rolUsuario.setNombre("ROLE_USUARIO");

        nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo("test@test.com");
        nuevoUsuario.setPassword("password123");
        nuevoUsuario.setRol(rolUsuario);
        nuevoUsuario.setNombre("Test");
        nuevoUsuario.setApellido("User");
    }

    @Test
    @DisplayName("Debería registrar un nuevo usuario correctamente y retornar 200")
    void registrarUsuario_Exito() throws Exception {
        given(usuarioRepository.findByCorreo(anyString())).willReturn(Optional.empty());
        given(rolRepository.findByNombre("ROLE_USUARIO")).willReturn(Optional.of(rolUsuario));
        given(passwordEncoder.encode(anyString())).willReturn("hashed_password");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setCorreo("test@test.com");
        usuarioGuardado.setPassword("hashed_password");
        usuarioGuardado.setRol(rolUsuario);
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioGuardado);

        mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado correctamente"));
    }

    @Test
    @DisplayName("No debería registrar un usuario si el correo ya existe y retornar 400")
    void registrarUsuario_CorreoExistente() throws Exception {
        given(usuarioRepository.findByCorreo(anyString())).willReturn(Optional.of(new Usuario()));

        mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El correo ya esta en uso ."));
    }

    @Test
    @DisplayName("Debería hacer login con credenciales correctas y retornar un token")
    void login_Exito() throws Exception {
        Map<String, String> loginRequest = Map.of("correo", "test@test.com", "password", "password123");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setCorreo("test@test.com");
        usuarioExistente.setPassword("hashed_password");
        usuarioExistente.setRol(rolUsuario);

        given(usuarioRepository.findByCorreo("test@test.com")).willReturn(Optional.of(usuarioExistente));
        given(passwordEncoder.matches("password123", "hashed_password")).willReturn(true);
        given(jwtUtils.generarToken("test@test.com")).willReturn("mock-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.correo").value("test@test.com"))
                .andExpect(jsonPath("$.rol").value("ROLE_USUARIO"));
    }

    @Test
    @DisplayName("No debería hacer login con contraseña incorrecta y retornar 401")
    void login_ContraseñaIncorrecta() throws Exception {
        Map<String, String> loginRequest = Map.of("correo", "test@test.com", "password", "wrong_password");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setCorreo("test@test.com");
        usuarioExistente.setPassword("hashed_password");
        usuarioExistente.setRol(rolUsuario);

        given(usuarioRepository.findByCorreo("test@test.com")).willReturn(Optional.of(usuarioExistente));
        given(passwordEncoder.matches("wrong_password", "hashed_password")).willReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales incorrectas"));
    }
}