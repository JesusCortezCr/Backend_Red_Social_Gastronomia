package com.app.backend_web.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.entities.Seguidor;

import static org.junit.jupiter.api.Assertions.*;
// O específicamente:
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableJpaRepositories(
    basePackages = "com.app.backend_web.repositories",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Seguidor.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = NotificacionRepository.class)
    }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RolRepositoryTest {

    @Autowired
    private RolRepository rolRepository;

    @Test
    void verificarRegistroRol(){
        Rol rol=new Rol();
        rol.setNombre("ROLE_ADMINISTRADOR");

        Rol rolRecibido=rolRepository.save(rol);
        assertEquals(rol, rolRecibido);

    }

}
