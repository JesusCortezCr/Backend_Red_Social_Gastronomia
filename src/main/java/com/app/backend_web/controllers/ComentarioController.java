package com.app.backend_web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.backend_web.entities.Comentario;
import com.app.backend_web.services.ComentarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    // ✅ Crear comentario asociado a una publicación
    @PostMapping("/publicacion/{publicacionId}/usuario/{usuarioId}")
    public ResponseEntity<?> crearComentario(
            @PathVariable Long publicacionId,
            @PathVariable Long usuarioId,
            @RequestBody Comentario comentario) {
        return ResponseEntity.status(201)
                .body(comentarioService.crearComentario(publicacionId, usuarioId, comentario));
    }
}
