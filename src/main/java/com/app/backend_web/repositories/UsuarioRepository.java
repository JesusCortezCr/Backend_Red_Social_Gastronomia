package com.app.backend_web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.backend_web.entities.Cliente;
import com.app.backend_web.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findByRolNombre(String nombreRol);
    
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Usuario> findByEstadoTrue();

    @Query("select u from Usuario u where TYPE(u)= Cliente and u.estado=true")
    List<Cliente> findAllClientesActivos();


    //Traer usuarios moderadores

    default List<Usuario> findAllUsuariosModeradores(){
        return findByRolNombre("ROLE_MODERADOR");
    }

    //traer usuarios administradores

    default List<Usuario> findAllUsuariosAdministradores(){
        return findByRolNombre("ROLE_ADMINISTRADOR");
    }
    

    
}
