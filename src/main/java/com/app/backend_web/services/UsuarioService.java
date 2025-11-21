package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // crear moderador,cliente,administrador

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarUsuariosPorRol(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol);
    }
    

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioCapturado = usuarioRepository.findById(id);
        if (usuarioCapturado.isPresent()) {
            return usuarioCapturado;
        }
        return usuarioCapturado.empty();
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
            usuarionuevo.setPassword(usuario.getPassword());
            usuarionuevo.setRol(usuario.getRol());
            usuarionuevo.setEstado(usuario.getEstado());
            return usuarioRepository.save(usuarionuevo);
        }
        return null;
    }

}
