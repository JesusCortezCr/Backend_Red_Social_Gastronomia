package com.app.backend_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

}
