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

    public List<Categoria> listadoCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarCategoriaPorId(Long id) {

        Optional<Categoria> categoriaCapturada = categoriaRepository.findById(id);
        if (categoriaCapturada.isPresent()) {

            return categoriaCapturada;
        }
        return categoriaCapturada.empty();
    }

    
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
