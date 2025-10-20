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

    @PostMapping("/publicacion/{publicacionId}/usuario/{usuarioId}")
    public ResponseEntity<?> crearComentario(
            @PathVariable Long publicacionId,
            @PathVariable Long usuarioId,
            @RequestBody Comentario comentario) {
        return ResponseEntity.status(201)
                .body(comentarioService.crearComentario(publicacionId, usuarioId, comentario));
    }

    @GetMapping
    public ResponseEntity<?> traerComentarios() {
        return ResponseEntity.ok().body(comentarioService.listadoComentarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerComentarioPorId(@PathVariable Long id) {
        if (comentarioService.buscarComentarioPorId(id).isPresent()) {
            return ResponseEntity.ok().body(comentarioService.buscarComentarioPorId(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearComentario(@RequestBody Comentario comentario) {
        Comentario nuevoComentario = comentarioService.crearComentario(comentario);
        return ResponseEntity.status(201).body(nuevoComentario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarComentario(@PathVariable Long id, @RequestBody Comentario comentario) {
        if (comentarioService.buscarComentarioPorId(id).isPresent()) {
            Comentario comentarioActualizado = comentarioService.actualizarComentario(id, comentario);
            return ResponseEntity.ok(comentarioActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> eliminarComentarioPorId(@PathVariable Long id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.status(204).build();
    }
}
