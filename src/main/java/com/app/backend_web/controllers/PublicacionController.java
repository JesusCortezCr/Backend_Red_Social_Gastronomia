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
import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.services.PublicacionService;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    private final PublicacionService publicacionService;
    
    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }
    
    @GetMapping()
    List<Publicacion> listadoPublicaciones() {
        return publicacionService.listadoTodasPublicaciones();
    }
    
    @GetMapping("/{id}")
    ResponseEntity<?> buscarPorPublicacionPorId(@PathVariable Long id) {
        Optional<Publicacion> publicacionOptional = publicacionService.obtenerPublicacionPorId(id);
        if (publicacionOptional.isPresent()) {
            return ResponseEntity.ok().body(publicacionOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> crearPublicacion(@RequestBody Publicacion publicacion) {
        Publicacion nuevaPublicacion = publicacionService.guardarPublicacion(1L, 1L, publicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPublicacion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPublicacion(@PathVariable Long id, @RequestBody Publicacion publicacion) {
        return publicacionService.actualizarPublicacion(id, publicacion).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPublicacion(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionService.obtenerPublicacionPorId(id);
        if (publicacion.isPresent()) {
            publicacionService.eliminarPublicacion(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}