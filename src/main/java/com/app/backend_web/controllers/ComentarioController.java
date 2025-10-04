package com.app.backend_web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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

//Diseñado Cortez Jesus
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping()
    List<Comentario> listadoComentarios() {
        return comentarioService.listadoTodosComentarios();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> buscarPorComentraioPorId(@PathVariable Long id) {
        Optional<Comentario> comentarioOptional = comentarioService.obtenerComentarioPorId(id);
        if (comentarioOptional.isPresent()) {
            return ResponseEntity.ok().body(comentarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearComentario(@RequestBody Comentario comentario) {
        Comentario nuevoComentario = comentarioService.guardarComentario(1L, 1L, comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoComentario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarComentario(@PathVariable Long id, @RequestBody Comentario comentario) {

        return comentarioService.actualizarComentario(id, comentario).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Long id) {
        Optional<Comentario> comentario = comentarioService.obtenerComentarioPorId(id);
        if (comentario.isPresent()) {
            comentarioService.eliminarComentario(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
