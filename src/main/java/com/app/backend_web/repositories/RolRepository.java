package com.app.backend_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
}
