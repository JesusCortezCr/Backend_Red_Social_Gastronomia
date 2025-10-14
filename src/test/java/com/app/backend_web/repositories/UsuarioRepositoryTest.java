package com.app.backend_web.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Usuario;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void crearUsuario() {
        
        Rol rol = new Rol();
        rol.setId(1L); 

        Usuario usuario = new Usuario();
        usuario.setCorreo("dani@gmail.com");
        usuario.setPassword("123");
        usuario.setNombre("Daniel");
        usuario.setApellido("Malpartida");
        usuario.setBiografia("Tres piernas");
        usuario.setRol(rol);
        usuario.setEstado(true);

        usuarioRepository.save(usuario);
        assertNotNull(usuarioRepository.findById(usuario.getId()).get());
    }
}
