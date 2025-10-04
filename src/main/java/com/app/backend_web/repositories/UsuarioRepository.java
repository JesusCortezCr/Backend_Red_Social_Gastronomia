package com.app.backend_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

}
