package com.app.backend_web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend_web.entities.Comentario;
import com.app.backend_web.services.ComentarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

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