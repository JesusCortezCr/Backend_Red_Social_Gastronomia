package com.app.backend_web.dto;

import lombok.Data;

@Data
public class ComentarioDto {
    private String contenido;
    private Long publicacionId;
    private Long usuarioId;
}