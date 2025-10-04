package com.app.backend_web.services;

import java.util.List;



import com.app.backend_web.entities.Usuario;


public interface UsuarioService {

    List<Usuario> listadoTodosUsuarios();
    
    Usuario obtenerUsuarioPorId(Long id);

    Usuario guardarUsuario(Usuario usuario, Long credencialesId, Long rolId);

    void eliminarUsuario(Long id);

    Usuario actualizarUsuario(Long id, Usuario usuario, Long credencialesId, Long rolId);


}
