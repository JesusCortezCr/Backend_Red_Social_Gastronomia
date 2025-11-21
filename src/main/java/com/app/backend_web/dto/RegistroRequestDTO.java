package com.app.backend_web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequestDTO {
    
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
}
