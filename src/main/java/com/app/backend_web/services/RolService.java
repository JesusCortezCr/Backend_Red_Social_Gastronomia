package com.app.backend_web.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.backend_web.entities.Rol;
import com.app.backend_web.repositories.RolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {
    
    private final RolRepository rolRepository;

    public List<Rol> listadoTodosRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> obtenerRolPorId(Long id) {
        return rolRepository.findById(id);
    }

    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Optional<Rol> actualizarRol(Long id, Rol rol) {
        return rolRepository.findById(id).map(pub -> {
            pub.setNombre(rol.getNombre());
            
            return rolRepository.save(pub);
        });
    }

    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }
}