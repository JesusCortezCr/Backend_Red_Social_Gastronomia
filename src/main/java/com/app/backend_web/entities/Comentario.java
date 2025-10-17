package com.app.backend_web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El contenido del comentario no puede estar vacío")
    @Size(min = 2, max = 500, message = "El comentario debe tener entre 2 y 500 caracteres")
    @Column(nullable = false)
    private String contenido;

    @NotNull(message = "El estado del comentario no puede ser nulo")
    private boolean estadoComentario = true;

    @NotNull(message = "La cantidad de reportes no puede ser nula")
    @Min(value = 0, message = "La cantidad de reportes no puede ser negativa")
    private Integer cantidadReportes = 0;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "publicacion_id", nullable = false)
    @JsonIgnore
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    @OneToMany(mappedBy = "comentarioPadre")
    @JsonIgnoreProperties("comentarioPadre")
    private List<Comentario> respuestas = new ArrayList<>();

    @Transient
    @Min(value = 0, message = "La cantidad de respuestas no puede ser negativa")
    private Integer cantidadRespuestas;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"publicaciones", "comentarios", "password", "seguidores", "siguiendo"})
    private Usuario usuario;

    @PostLoad
    private void calcularRespuestas() {
        this.cantidadRespuestas = respuestas != null ? respuestas.size() : 0;
    }
}
