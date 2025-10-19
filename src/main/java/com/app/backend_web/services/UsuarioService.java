package com.app.backend_web.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.RolRepository;
import com.app.backend_web.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

@Transactional
    public Usuario registrarNuevoUsuario(Usuario nuevoUsuario) {
        if (usuarioRepository.existsByCorreo(nuevoUsuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está en uso.");
        }
        Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("Rol ROLE_USUARIO no encontrado"));

        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        nuevoUsuario.setRol(rolUsuario);

        return usuarioRepository.save(nuevoUsuario);
    }

    @Transactional
    public Usuario cambiarRolDeUsuario(Long usuarioId, String nuevoNombreRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + usuarioId));

        Rol nuevoRol = rolRepository.findByNombre(nuevoNombreRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con nombre: " + nuevoNombreRol));

        usuario.setRol(nuevoRol);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarUsuarios'");
    }
}
