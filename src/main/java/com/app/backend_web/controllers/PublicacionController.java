package com.app.backend_web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.backend_web.dto.PublicacionRequest;
import com.app.backend_web.entities.Publicacion;
import com.app.backend_web.services.PublicacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publicaciones")
public class PublicacionController {

    private static final Logger logger = LoggerFactory.getLogger(PublicacionController.class);
    private final PublicacionService publicacionService;


    @GetMapping
    public ResponseEntity<?> listarPublicaciones() {
        return ResponseEntity.ok(publicacionService.listarPublicaciones());
    }

    @PostMapping
    public ResponseEntity<?> savePublicacion(@RequestPart("publicacion") PublicacionRequest publicacion , @RequestPart("file") MultipartFile file, Principal principal){
        try {
            System.out.println("=== INICIO POST PUBLICACION ===");
            System.out.println("Principal: " + principal.getName());
            System.out.println("Publicacion: " + publicacion);
            System.out.println("File: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());
            
            String userEmail = principal.getName();
            Publicacion publicacionGuardada = publicacionService.guardarPublicacion(publicacion, file, userEmail);
            
            System.out.println("✅ Publicacion guardada con ID: " + publicacionGuardada.getId());
            return ResponseEntity.ok(publicacionGuardada);
            
        } catch (Exception e) {
            System.err.println("❌ ERROR al guardar publicacion:");
            e.printStackTrace();
            
            // DEVOLVER EL ERROR AL FRONTEND
            return ResponseEntity
                .status(org.springframework.http.HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Publicacion> updatePublicacionImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Publicacion> publicacion = publicacionService.buscarPublicacionPorId(id);
        if (publicacion.isPresent()) {
            Publicacion updatedPublicacion = publicacionService.updatePublicacionImage(file, publicacion.get());
            return new ResponseEntity<>(updatedPublicacion, org.springframework.http.HttpStatus.OK);
        }else {
            return new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/mis-publicaciones")
    public ResponseEntity<List<Publicacion>> listarMisPublicaciones(Principal principal){
        String email = principal.getName();
        logger.info("⚙️ [Controller] Buscando publicaciones para el email: {}", email);
        List<Publicacion> misPublicacioens=publicacionService.listarPublicacionesPorUsuario(email);
        return ResponseEntity.ok(misPublicacioens);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPublicacion(@PathVariable Long id,Principal principal){
        try{
            String userEmail=principal.getName();
            publicacionService.eliminarPublicacion(id,userEmail);
            return ResponseEntity.ok(Map.of("message","Publicacion eliminada"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
}
