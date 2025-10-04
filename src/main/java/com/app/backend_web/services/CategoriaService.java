package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.backend_web.entities.Categoria;
import com.app.backend_web.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listadoTodasCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> actualizarCategoria(Long id, Categoria categoria) {
        return categoriaRepository.findById(id).map(pub -> {
            pub.setNombre(categoria.getNombre());
            
            return categoriaRepository.save(pub);
        });
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}