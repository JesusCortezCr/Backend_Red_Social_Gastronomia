package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend_web.entities.Comentario;
import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.ComentarioRepository;
import com.app.backend_web.repositories.PublicacionRepository;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Comentario crearComentario(Long publicacionId, Long usuarioId, Comentario comentario) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + publicacionId));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        comentario.setPublicacion(publicacion);
        comentario.setUsuario(usuario);

        return comentarioRepository.save(comentario);
    }

    @Transactional
    public List<Comentario> listadoComentarios() {
        return comentarioRepository.findAll();
    }

    @Transactional
    public Optional<Comentario> buscarComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    @Transactional
    public Comentario crearComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Transactional
    public Comentario actualizarComentario(Long id, Comentario comentarioDetails) {
        Comentario comentarioExistente = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con id: " + id));
        comentarioExistente.setContenido(comentarioDetails.getContenido());
        return comentarioRepository.save(comentarioExistente);
    }

    @Transactional
    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }

}
