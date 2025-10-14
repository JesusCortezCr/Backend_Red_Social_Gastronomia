package com.app.backend_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.backend_web.entities.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long>{

}
