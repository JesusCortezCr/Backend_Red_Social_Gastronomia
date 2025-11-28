package com.app.backend_web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublicacionRequest {
    private String titulo;
    private String descripcion;
    private Long categoriaId;
}
