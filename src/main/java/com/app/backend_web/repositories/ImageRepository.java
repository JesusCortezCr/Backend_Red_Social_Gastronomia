package com.app.backend_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.backend_web.entities.Image;

public interface ImageRepository extends JpaRepository<Image,Long>{

}
