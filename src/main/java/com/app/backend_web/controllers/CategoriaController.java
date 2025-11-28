package com.app.backend_web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.backend_web.entities.Categoria;
import com.app.backend_web.exceptions.ResourceNotFoundException;
import com.app.backend_web.services.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listadoCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCategoriaPorId(@PathVariable Long id) {
        return categoriaService.buscarCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + id + " no encontrada"));
    }

    @PostMapping
    public ResponseEntity<?> crearCategoria(@Valid @RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.crearCategoria(categoria);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody Categoria nuevaCategoria) {

        Categoria actualizada = categoriaService.actualizarCategoria(id, nuevaCategoria);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
