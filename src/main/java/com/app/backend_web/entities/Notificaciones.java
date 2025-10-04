package com.app.backend_web.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notificaciones {

    private Long id;
    private Cliente clienteDestino;
    private Cliente clienteOrigen;
    private String titulo;
    private String mensaje;
    private String tipoNotificacion; 
}
