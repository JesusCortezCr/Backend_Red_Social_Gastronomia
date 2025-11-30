package com.app.backend_web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.backend_web.entities.Usuario;
import com.app.backend_web.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<?> traerUsuarios() {
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
            .map(usuario -> ResponseEntity.ok(usuario))
            .orElse(ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.buscarUsuarioPorId(id).isPresent()) {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok("Usuario eliminado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {

        Usuario nuevo = usuarioService.actualizarUsuario(id, usuario);
        if (nuevo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(nuevo);
    }

    @GetMapping("/rol/{nombreRol}")
    public ResponseEntity<?> listarUsuariosPorRol(@PathVariable String nombreRol) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPorRol(nombreRol));
    }

}
