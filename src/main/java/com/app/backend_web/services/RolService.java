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

    // 🔹 Listar todos los roles
    public List<Rol> listadoRoles() {
        return rolRepository.findAll();
    }

    // 🔹 Buscar rol por ID
    public Optional<Rol> buscarRolPorId(Long id) {
        return rolRepository.findById(id);
    }

    // 🔹 Buscar rol por nombre
    public Optional<Rol> buscarPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    // 🔹 Crear un nuevo rol
    public Rol crearRol(Rol rol) {
        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío");
        }
        return rolRepository.save(rol);
    }

    // 🔹 Actualizar un rol existente
    public Rol actualizarRol(Long id, Rol nuevoRol) {
        Rol rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));

        rolExistente.setNombre(nuevoRol.getNombre());
        return rolRepository.save(rolExistente);
    }

    // 🔹 Eliminar un rol por ID
    public void eliminarRol(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
        rolRepository.delete(rol);
    }
}
