package com.app.backend_web.entities;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente  extends Usuario{

    private Long id;
    private Usuario usuario;
    private Integer cantidadSeguidores;
}
