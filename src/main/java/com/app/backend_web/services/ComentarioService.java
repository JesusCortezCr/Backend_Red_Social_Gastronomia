package com.app.backend_web.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend_web.dto.ComentarioDto;
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
    public List<Comentario> listadoComentarios() {
        return comentarioRepository.findAll();
    }

    @Transactional
    public Optional<Comentario> buscarComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    @Transactional
public Comentario crearComentario(ComentarioDto comentarioRequest) {
    try {
        System.out.println("🔍 Validando comentario request...");
        
        // Validar y cargar la publicación
        Publicacion publicacion = publicacionRepository.findById(comentarioRequest.getPublicacionId())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + comentarioRequest.getPublicacionId()));
        
        // Validar y cargar el usuario
        Usuario usuario = usuarioRepository.findById(comentarioRequest.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + comentarioRequest.getUsuarioId()));

        // Crear entidad Comentario
        Comentario comentario = new Comentario();
        comentario.setContenido(comentarioRequest.getContenido());
        comentario.setPublicacion(publicacion);
        comentario.setUsuario(usuario);
        comentario.setFechaCreacion(LocalDateTime.now());
        comentario.setEstadoComentario(true);
        comentario.setCantidadReportes(0);

        System.out.println("💾 Guardando comentario...");
        Comentario saved = comentarioRepository.save(comentario);
        System.out.println("✅ Comentario guardado con ID: " + saved.getId());
        
        return saved;
        
    } catch (Exception e) {
        System.err.println("❌ Error en servicio: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al crear comentario: " + e.getMessage());
    }
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

    @Transactional
    public List<Comentario> obtenerComentariosPorPublicacion(Long publicacionId) {
        return comentarioRepository.findByPublicacionIdAndComentarioPadreIsNull(publicacionId);
    }

    @Transactional
    public Comentario responderComentario(Long comentarioPadreId, Long usuarioId, Comentario respuesta) {
        Comentario comentarioPadre = comentarioRepository.findById(comentarioPadreId)
                .orElseThrow(() -> new RuntimeException("Comentario padre no encontrado con ID: " + comentarioPadreId));
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        respuesta.setComentarioPadre(comentarioPadre);
        respuesta.setUsuario(usuario);
        respuesta.setPublicacion(comentarioPadre.getPublicacion());

        return comentarioRepository.save(respuesta);
    }

}
