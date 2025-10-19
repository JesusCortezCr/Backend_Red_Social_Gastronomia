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

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.services.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> traerCategorias() {
        return ResponseEntity.ok().body(categoriaService.listadoCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerCategoriaPorId(@PathVariable Long id) {
        if (categoriaService.buscarCategoriaPorId(id).isPresent()) {
            return ResponseEntity.ok().body(categoriaService.buscarCategoriaPorId(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
        return ResponseEntity.status(201).body(nuevaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        if (categoriaService.buscarCategoriaPorId(id).isPresent()) {
            Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDetails);
            return ResponseEntity.ok(categoriaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> eliminarCategoriaPorId(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.status(204).build();
    }

}
