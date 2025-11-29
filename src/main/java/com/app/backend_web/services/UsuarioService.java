package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // crear moderador,cliente,administrador

    public java.util.List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public java.util.List<Usuario> listarUsuariosPorRol(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol);
    }
    

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
    return usuarioRepository.findById(id);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuarionuevo = usuarioExistente.get();

            usuarionuevo.setNombre(usuario.getNombre());
            usuarionuevo.setApellido(usuario.getApellido());
            usuarionuevo.setCorreo(usuario.getCorreo());

            //Verificacion de cambio de contraseña y comparacion
            String nuevoPassword = usuario.getPassword();
            if(nuevoPassword != null && !nuevoPassword.isEmpty()) {
                usuarionuevo.setPassword(passwordEncoder.encode(nuevoPassword));
            } else {
                usuarionuevo.setPassword(usuarionuevo.getPassword());
            }

            usuarionuevo.setBiografia(usuario.getBiografia());
            usuarionuevo.setRol(usuario.getRol());
            usuarionuevo.setEstado(usuario.getEstado());
            return usuarioRepository.save(usuarionuevo);
        }
        return null;
    }

}
