package com.app.backend_web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.backend_web.services.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    @GetMapping()
    public ResponseEntity<?> traerRoles() {
        return ResponseEntity.ok().body(rolService.listadoRoles());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarRolporId(@PathVariable Long id) {
        
        if(rolService.buscarRolPorId(id).isPresent()){
            return ResponseEntity.ok().body(rolService.buscarRolPorId(id).get());
        }
        return null;
    }
    
    
}
