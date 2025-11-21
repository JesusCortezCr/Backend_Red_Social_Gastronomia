package com.app.backend_web.dto;

import org.springframework.stereotype.Component;

import com.app.backend_web.entities.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol().getNombre());
    }
}
