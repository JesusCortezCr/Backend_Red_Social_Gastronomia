package com.app.backend_web.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.CategoriaRepository;
import com.app.backend_web.repositories.PublicacionRepository;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Publicacion crearPublicacion(Long usuarioId, Long categoriaId, Publicacion publicacion) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));

        publicacion.setUsuario(usuario);
        publicacion.setCategoria(categoria);

        return publicacionRepository.save(publicacion);
    }

    public List<Publicacion> listarPublicaciones() {
        return publicacionRepository.findAll();
    }
}
