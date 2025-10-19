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

    public List<Rol> listadoRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarRolPorId(Long id) {
        Optional<Rol> rolCapturado = rolRepository.findById(id);
        if (rolCapturado.isPresent()) {
            return rolCapturado;
        }
        return rolCapturado.empty();
    }
}
