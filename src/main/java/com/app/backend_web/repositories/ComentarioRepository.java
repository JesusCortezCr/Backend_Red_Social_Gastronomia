package com.app.backend_web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario,Long>{

     List<Comentario> findByPublicacionIdAndComentarioPadreIsNull(Long publicacionId);
}
