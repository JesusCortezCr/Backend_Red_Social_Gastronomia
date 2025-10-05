package com.app.backend_web.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.repositories.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void creacionCategoriaVerificar() {

        Categoria categoria = new Categoria();
        categoria.setNombre("Desayuno");

        Categoria categoriaEsperada = new Categoria();
        categoriaEsperada.setId(1L); 
        categoriaEsperada.setNombre("Desayuno");
        
        when(categoriaRepository.save(categoria)).thenReturn(categoriaEsperada);

        assertEquals(categoriaEsperada, categoriaService.crearCategoria(categoria));

    }
}
