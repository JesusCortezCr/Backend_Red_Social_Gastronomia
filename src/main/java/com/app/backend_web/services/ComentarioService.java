package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;

import com.app.backend_web.entities.Comentario;

public interface ComentarioService {

    List<Comentario> listadoTodosComentarios();
    Optional<Comentario> obtenerComentarioPorId(Long id);
    Comentario guardarComentario(Long usuarioId,Long PublicacionId, Comentario comentario);
    void eliminarComentario(Long id);
    Optional<Comentario> actualizarComentario(Long id, Comentario comentario);
}
