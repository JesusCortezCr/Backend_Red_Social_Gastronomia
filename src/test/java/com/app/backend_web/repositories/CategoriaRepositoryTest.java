package com.app.backend_web.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.app.backend_web.entities.Categoria;
import com.app.backend_web.entities.Seguidor;

@DataJpaTest
@EnableJpaRepositories(
    basePackages = "com.app.backend_web.repositories",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Seguidor.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = NotificacionRepository.class)
    }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void cuandoGuardaCategoria_luegoCategoriaPersistida() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Desayuno");
        
        Categoria guardada = categoriaRepository.save(categoria);
        
        assertNotNull(guardada, "La categoría guardada no debería ser null");
        assertNotNull(guardada.getId(), "La categoría debería tener un ID generado");
        assertEquals("Desayuno", guardada.getNombre(), "El nombre debería ser 'Desayuno'");
    }

    @Test
    void cuandoBuscaCategoriaPorId_entoncesRetornaCategoriaCorrecta() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Almuerzo");
        Categoria guardada = categoriaRepository.save(categoria);
        
        Optional<Categoria> encontrada = categoriaRepository.findById(guardada.getId());
        
        assertTrue(encontrada.isPresent(), "La categoría debería existir");
        assertEquals("Almuerzo", encontrada.get().getNombre());
    }

    @Test
    void cuandoBuscaTodasLasCategorias_entoncesRetornaLista() {
        Categoria cat1 = new Categoria();
        cat1.setNombre("Cena");
        categoriaRepository.save(cat1);

        Categoria cat2 = new Categoria();
        cat2.setNombre("Postres");
        categoriaRepository.save(cat2);
        
        List<Categoria> categorias = categoriaRepository.findAll();
        
        assertNotNull(categorias);
        assertTrue(categorias.size() >= 2, "Debería haber al menos 2 categorías");
    }

    @Test
    void cuandoActualizaCategoria_entoncesCambioEsPersistido() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Entradas");
        Categoria guardada = categoriaRepository.save(categoria);
        
        guardada.setNombre("Aperitivos");
        categoriaRepository.save(guardada);
        
        Optional<Categoria> actualizada = categoriaRepository.findById(guardada.getId());
        
        assertTrue(actualizada.isPresent());
        assertEquals("Aperitivos", actualizada.get().getNombre());
    }

    @Test
    void cuandoEliminaCategoriaPorId_entoncesYaNoExiste() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Bebidas");
        Categoria guardada = categoriaRepository.save(categoria);
        Long id = guardada.getId();
        
        categoriaRepository.deleteById(id);
        
        Optional<Categoria> eliminada = categoriaRepository.findById(id);
        assertFalse(eliminada.isPresent(), "La categoría no debería existir después de ser eliminada");
    }
}