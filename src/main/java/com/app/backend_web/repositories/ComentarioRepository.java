package com.app.backend_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario,Long>{

}
