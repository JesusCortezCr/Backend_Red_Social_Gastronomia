package com.app.backend_web.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend_web.configuration.JwtUtils;
import com.app.backend_web.dto.AuthResponse;
import com.app.backend_web.dto.LoginRequest;
import com.app.backend_web.dto.RegistroRequestDTO;
import com.app.backend_web.dto.UsuarioMapper;
import com.app.backend_web.dto.UsuarioResponseDTO;
import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.RolRepository;
import com.app.backend_web.repositories.UsuarioRepository;
import com.app.backend_web.services.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final TokenBlacklistService tokenBlacklistService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequestDTO nuevoUsuario) {
        if (usuarioRepository.findByCorreo(nuevoUsuario.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya esta en uso .");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setApellido(nuevoUsuario.getApellido());
        usuario.setCorreo(nuevoUsuario.getCorreo());
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rolUsuario);
        usuarioRepository.save(usuario);
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setCorreo(usuario.getCorreo());
        usuarioResponseDTO.setNombre(usuario.getNombre());
        usuarioResponseDTO.setApellido(usuario.getApellido());
        usuarioResponseDTO.setBiografia(usuario.getBiografia());
        usuarioResponseDTO.setRolNombre(usuario.getRol().getNombre());
        usuarioResponseDTO.setEstado(usuario.getEstado());
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginData.getCorreo(),
                            loginData.getPassword()));

        
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

      
            String token = jwtUtils.generarToken(userDetails);

            
            Usuario usuario = usuarioRepository.findByCorreo(loginData.getCorreo())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(token);
            authResponse.setCorreo(usuario.getCorreo());
            authResponse.setRol(usuario.getRol().getNombre());
            authResponse.setId(usuario.getId());

            return ResponseEntity.ok(authResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        } catch (Exception e) {
            // Se recomienda loguear la excepción completa aquí: logger.error("Error en
            // login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor: " + e.getMessage());
        }
    }

    // obtener usuario actual
    @GetMapping("/logeado")
    public ResponseEntity<?> obtenerUsuarioLogeado(Authentication authenticado, UsuarioMapper usuarioMapper) {
        if (authenticado.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        String correo = authenticado.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));

    }

    // Cerrar sesion logout
    @PostMapping("/logout")
    public ResponseEntity<?> cerrarSesion(HttpServletRequest request) {
        String token = traerTokenDelRequest(request);
        if (token != null && jwtUtils.validarToken(token)) {
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok().body(Map.of("message", "Logout exitoso"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Token invalido"));
    }

    private String traerTokenDelRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}