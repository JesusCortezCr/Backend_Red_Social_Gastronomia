package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.repositories.CategoriaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public List<Categoria> listadoCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional
    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }
    
    // Operación de escritura
    @Transactional
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria categoriaDetails) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
        categoriaExistente.setNombre(categoriaDetails.getNombre());
        return categoriaRepository.save(categoriaExistente);
    }

    // Operación de escritura
    @Transactional
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

}
