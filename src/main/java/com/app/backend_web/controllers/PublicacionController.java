package com.app.backend_web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.services.PublicacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publicaciones")
public class PublicacionController {

    private final PublicacionService publicacionService;

    // ✅ Crear una publicación asociada a un usuario y categoría existentes
    @PostMapping("/usuario/{usuarioId}/categoria/{categoriaId}")
    public ResponseEntity<?> crearPublicacion(
            @PathVariable Long usuarioId,
            @PathVariable Long categoriaId,
            @RequestBody Publicacion publicacion) {
        return ResponseEntity.status(201)
                .body(publicacionService.crearPublicacion(usuarioId, categoriaId, publicacion));
    }

    @GetMapping
    public ResponseEntity<?> listarPublicaciones() {
        return ResponseEntity.ok(publicacionService.listarPublicaciones());
    }
}
