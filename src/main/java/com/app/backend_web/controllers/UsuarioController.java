package com.app.backend_web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.backend_web.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;
    
    @GetMapping()
    public ResponseEntity<?> traerUsuarios() {
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }
    
}
