package com.app.backend_web.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Comentario;
import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.ComentarioRepository;
import com.app.backend_web.repositories.PublicacionRepository;
import com.app.backend_web.repositories.UsuarioRepository;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final PublicacionRepository publicacionRepository;

    private final UsuarioRepository usuarioRepository;

    private final ComentarioRepository comentarioRepository;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository,UsuarioRepository usuarioRepository,PublicacionRepository publicacionRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    public List<Comentario> listadoTodosComentarios() {
        return comentarioRepository.findAll();
    }

    @Override
    public Optional<Comentario> obtenerComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    @Override
    public Comentario guardarComentario(Long usuarioId, Long PublicacionId, Comentario comentario) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

    Publicacion publicacion = publicacionRepository.findById(PublicacionId)
            .orElseThrow(() -> new RuntimeException("Publicación no encontrada con id: " + PublicacionId));

        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setContenido(comentario.getContenido());
        nuevoComentario.setEstadoComentario(true);
        nuevoComentario.setCantidadReportes(0);
        nuevoComentario.setFechaCreacion(LocalDateTime.now());
        nuevoComentario.setPublicacion(publicacion);
        nuevoComentario.setUsuario(usuario);
        return comentarioRepository.save(nuevoComentario);
    }

    @Override
    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }

    @Override
    public Optional<Comentario> actualizarComentario(Long id, Comentario comentario) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
        if (comentarioOptional.isPresent()) {
            Comentario comentarioExistente = comentarioOptional.get();
            comentarioExistente.setContenido(comentario.getContenido());
            comentarioExistente.setFechaCreacion(LocalDateTime.now());
            Comentario comentarioActualizado = comentarioRepository.save(comentarioExistente);
            return Optional.of(comentarioActualizado);
        }
        return Optional.empty();
    }

}
