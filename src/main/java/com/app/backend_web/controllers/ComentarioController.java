package com.app.backend_web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.backend_web.dto.ComentarioDto;
import com.app.backend_web.entities.Comentario;
import com.app.backend_web.services.ComentarioService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comentarios")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearComentario(@RequestBody ComentarioDto comentarioDto) {
        try {
        System.out.println("📝 Creando comentario:");
        System.out.println("   Contenido: " + comentarioDto.getContenido());
        System.out.println("   Publicación ID: " + comentarioDto.getPublicacionId());
        System.out.println("   Usuario ID: " + comentarioDto.getUsuarioId());
        
        Comentario nuevoComentario = comentarioService.crearComentario(comentarioDto);
        return ResponseEntity.ok(nuevoComentario);
        
    } catch (RuntimeException e) {
        System.err.println("❌ Error al crear comentario: " + e.getMessage());
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("❌ Error inesperado: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> listadoComentarios() {
        try {
            List<Comentario> comentarios = comentarioService.listadoComentarios();
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscarComentarioPorId(@PathVariable Long id) {
        try {
            Optional<Comentario> comentario = comentarioService.buscarComentarioPorId(id);
            return comentario.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarComentario(@PathVariable Long id, @RequestBody Comentario comentarioDetails) {
        try {
            Comentario comentarioActualizado = comentarioService.actualizarComentario(id, comentarioDetails);
            return ResponseEntity.ok(comentarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long id) {
        try {
            comentarioService.eliminarComentario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/publicacion/{publicacionId}")
    public ResponseEntity<?> obtenerComentariosPorPublicacion(@PathVariable Long publicacionId) {
        try {
            List<Comentario> comentarios = comentarioService.obtenerComentariosPorPublicacion(publicacionId);
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{comentarioPadreId}/respuestas")
    @Transactional
    public ResponseEntity<?> responderComentario(@PathVariable Long comentarioPadreId,
            @RequestBody Comentario respuesta) {
        try {
            if (respuesta.getUsuario() == null || respuesta.getUsuario().getId() == null) {
                return ResponseEntity.badRequest().body("El usuario es requerido");
            }

            Long usuarioId = respuesta.getUsuario().getId();
            Comentario respuestaCreada = comentarioService.responderComentario(comentarioPadreId, usuarioId, respuesta);
            return ResponseEntity.ok(respuestaCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}