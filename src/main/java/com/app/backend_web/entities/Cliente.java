package com.app.backend_web.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"seguidores", "siguiendo"})
public class Cliente extends Usuario {

    // Lista de seguidores del cliente
    @OneToMany(mappedBy = "seguido")
    @JsonIgnore
    private List<Seguidor> seguidores;

    // Lista de usuarios a los que sigue
    @OneToMany(mappedBy = "seguidor")
    @JsonIgnore
    private List<Seguidor> siguiendo;

    // Cantidad de seguidores (solo lectura)
    @Transient
    @Min(value = 0, message = "La cantidad de seguidores no puede ser negativa")
    private Integer cantidadSeguidores;

    // Cantidad de seguidos (solo lectura)
    @Transient
    @Min(value = 0, message = "La cantidad de seguidos no puede ser negativa")
    private Integer cantidadSiguiendo;

    @PostLoad
    private void calcularEstadisticas() {
        this.cantidadSeguidores = seguidores != null ? seguidores.size() : 0;
        this.cantidadSiguiendo = siguiendo != null ? siguiendo.size() : 0;
    }
}
