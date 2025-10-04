package com.app.backend_web.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"seguidores", "siguiendo"})
public class Cliente  extends Usuario{

    //mis seguidores
    @OneToMany(mappedBy = "seguido")
    @JsonIgnore
    private List<Seguidor> seguidores;

    @OneToMany(mappedBy = "seguidor")
    @JsonIgnore
    private List<Seguidor> siguiendo;

    @Transient
    private Integer cantidadSeguidores;

    @Transient
    private Integer cantidadSiguiendo;

    @PostLoad
    private void calcularEstadisticas() {
        this.cantidadSeguidores = seguidores != null ? seguidores.size() : 0;
        this.cantidadSiguiendo = siguiendo != null ? siguiendo.size() : 0;
    }
}
