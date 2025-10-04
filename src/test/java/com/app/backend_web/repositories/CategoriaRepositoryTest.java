package com.app.backend_web.repositories;

import com.app.backend_web.entities.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void guardarCategoria() {
        Categoria categoria = new Categoria(null, "Entradas");
        
        Categoria resultado = categoriaRepository.save(categoria);
        
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Entradas");
    }

    @Test
    public void buscarPorId() {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Platos Principales"));
        Long id = categoria.getId();
        
        Categoria resultado = categoriaRepository.findById(id).orElse(null);
        
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Platos Principales");
    }

    @Test
    public void eliminarCategoria() {
        Categoria categoria = categoriaRepository.save(new Categoria(null, "Sopas"));
        Long id = categoria.getId();
        
        categoriaRepository.deleteById(id);
        
        assertThat(categoriaRepository.findById(id)).isEmpty();
    }
}