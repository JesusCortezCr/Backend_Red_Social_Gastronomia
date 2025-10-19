package com.app.backend_web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.exceptions.ResourceNotFoundException;
import com.app.backend_web.services.RolService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    // 🔹 Listar todos los roles
    @GetMapping
    public ResponseEntity<?> listarRoles() {
        return ResponseEntity.ok(rolService.listadoRoles());
    }

    // 🔹 Buscar rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarRolPorId(@PathVariable Long id) {
        return rolService.buscarRolPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Rol con ID " + id + " no encontrado"));
    }

    // 🔹 Crear un nuevo rol
    @PostMapping
    public ResponseEntity<?> crearRol(@Valid @RequestBody Rol rol) {
        Rol nuevo = rolService.crearRol(rol);
        return ResponseEntity.ok(nuevo);
    }

    // 🔹 Actualizar un rol existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(
            @PathVariable Long id,
            @Valid @RequestBody Rol nuevoRol) {

        Rol actualizado = rolService.actualizarRol(id, nuevoRol);
        return ResponseEntity.ok(actualizado);
    }

    // 🔹 Eliminar un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
