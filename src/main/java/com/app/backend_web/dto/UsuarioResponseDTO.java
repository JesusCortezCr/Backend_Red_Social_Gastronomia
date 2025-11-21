package com.app.backend_web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String correo;
    private String nombre;
    private String apellido;
    private String biografia;
    private String rolNombre;
    private Boolean estado;
}
