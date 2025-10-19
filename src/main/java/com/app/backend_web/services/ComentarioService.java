package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Comentario;
import com.app.backend_web.repositories.ComentarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Transactional
    public List<Comentario> listadoComentarios() {
        return comentarioRepository.findAll();
    }

    @Transactional
    public Optional<Comentario> buscarComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }
    
    // Operación de escritura
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

    // Operación de escritura
    @Transactional
    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }

}