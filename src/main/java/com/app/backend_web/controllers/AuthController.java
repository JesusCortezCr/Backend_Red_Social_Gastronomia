package com.app.backend_web.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend_web.configuration.JwtUtils;
import com.app.backend_web.dto.LoginRequest;
import com.app.backend_web.dto.RegistroRequestDTO;
import com.app.backend_web.dto.UsuarioMapper;
import com.app.backend_web.dto.UsuarioResponseDTO;
import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.RolRepository;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequestDTO nuevoUsuario) {
        if (usuarioRepository.findByCorreo(nuevoUsuario.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya esta en uso .");
        }
        Usuario usuario=new Usuario();
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setApellido(nuevoUsuario.getApellido());
        usuario.setCorreo(nuevoUsuario.getCorreo());
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        Rol rolUsuario=rolRepository.findByNombre("ROLE_USUARIO")
        .orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rolUsuario);
        usuarioRepository.save(usuario);
        UsuarioResponseDTO usuarioResponseDTO=new UsuarioResponseDTO();
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
        String correo = loginData.getCorreo();
        String password =loginData.getPassword();

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        String token = jwtUtils.generarToken(usuario.getCorreo());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "correo", usuario.getCorreo(),
                "rol", usuario.getRol().getNombre()));
    }


    //obtener usuario actual
    @GetMapping("/logeado")
    public ResponseEntity<?> obtenerUsuarioLogeado(Authentication authenticado , UsuarioMapper usuarioMapper){
        if(authenticado.getPrincipal()==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        String correo= authenticado.getName();
        Usuario usuario= usuarioRepository.findByCorreo(correo).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));

    }
}
