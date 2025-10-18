package com.app.backend_web.services;




import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Rol;
import com.app.backend_web.repositories.RolRepository;

import jakarta.transaction.Transactional;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {


    private final RolRepository rolRepository;

    // Es una operación de solo lectura. readOnly=true es una buena práctica.
    @Transactional
    public List<Rol> listadoRoles() {
        return rolRepository.findAll();
    }

    // Es una operación de solo lectura.
    @Transactional
    public Optional<Rol> buscarRolPorId(Long id) {
        return rolRepository.findById(id); // El Optional ya maneja el caso de que no exista
    }
    
    // Es una operación de escritura, por lo tanto, debe ser transaccional.
    @Transactional
    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }
}
