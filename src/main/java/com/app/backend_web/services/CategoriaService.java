package com.app.backend_web.services;

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listadoCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria nuevaCategoria) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        categoria.setNombre(nuevaCategoria.getNombre());
       

        return categoriaRepository.save(categoria);
    }
}
