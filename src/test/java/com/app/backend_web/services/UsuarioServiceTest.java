package com.app.backend_web.services;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.RolRepository;
import com.app.backend_web.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Rol rolUsuario;
    private Usuario nuevoUsuario;

    @BeforeEach
    void setUp() {
        rolUsuario = new Rol(1L, "ROLE_USER");
        nuevoUsuario = new Usuario(null, "nuevo@test.com", "plainPassword", true, null, "Nuevo", "Usuario", null);
    }

    @Test
    @DisplayName("debería lanzar excepción si el correo ya existe al registrar")
    void registrarNuevoUsuario_EmailExists_ThrowsException() {
        when(usuarioRepository.existsByCorreo(anyString())).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.registrarNuevoUsuario(nuevoUsuario))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El correo ya está en uso.");
        
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("debería cambiar el rol de un usuario exitosamente (Punto 3 y 4)")
    void cambiarRolDeUsuario_Success() {
        Long usuarioId = 1L;
        String nuevoNombreRol = "ROLE_ADMIN";
        Rol rolAdmin = new Rol(2L, nuevoNombreRol);
        Usuario usuarioExistente = new Usuario(usuarioId, "user@test.com", "pass", true, rolUsuario, "User", "Test", null);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(rolRepository.findByNombre(nuevoNombreRol)).thenReturn(Optional.of(rolAdmin));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.cambiarRolDeUsuario(usuarioId, nuevoNombreRol);

        assertThat(resultado.getRol().getNombre()).isEqualTo(nuevoNombreRol);
        verify(usuarioRepository).findById(usuarioId);
        verify(rolRepository).findByNombre(nuevoNombreRol);
        verify(usuarioRepository).save(usuarioExistente);
    }

    @Test
    @DisplayName("debería lanzar excepción si el usuario no se encuentra al cambiar rol")
    void cambiarRolDeUsuario_UserNotFound_ThrowsException() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.cambiarRolDeUsuario(99L, "ROLE_ADMIN"))
                .isInstanceOf(EntityNotFoundException.class);
        
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}