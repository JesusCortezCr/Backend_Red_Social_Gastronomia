package com.app.backend_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion,Long> {

}
