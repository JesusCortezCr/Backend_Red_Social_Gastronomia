package com.app.backend_web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.entities.Usuario;

public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {

    List<Publicacion> findByUsuario(Usuario usuario);
    

}
