package com.app.backend_web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.backend_web.entities.Usuario;
import com.app.backend_web.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    //crear moderador,cliente,administrador

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
    
}
