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

    // 🟢 Listar todas las categorías
    public List<Categoria> listadoCategorias() {
        return categoriaRepository.findAll();
    }

    // 🟢 Buscar categoría por ID
    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    // 🟢 Crear una nueva categoría
    @Transactional
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // 🟢 Eliminar una categoría por ID
    @Transactional
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    // 🟢 Actualizar una categoría existente
    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria nuevaCategoria) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        categoria.setNombre(nuevaCategoria.getNombre());
       

        return categoriaRepository.save(categoria);
    }
}
