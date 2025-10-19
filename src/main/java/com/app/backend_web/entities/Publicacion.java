package com.app.backend_web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publicaciones")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres")
    private String descripcion;

    @NotBlank(message = "El contenido es obligatorio")
    @Size(min = 10, message = "El contenido debe tener al menos 10 caracteres")
    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Size(max = 255, message = "La URL de la imagen no puede superar los 255 caracteres")
    private String imagenUrl;

    @NotNull(message = "El estado no puede ser nulo")
    private boolean estado = true;

    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer calificacion;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private LocalDateTime fechaActualizacion;

    @Size(max = 255, message = "El nombre del archivo no puede superar los 255 caracteres")
    private String archivo;

    @NotNull(message = "La publicación debe estar asociada a un usuario")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"publicaciones", "comentarios", "password"})
    private Usuario usuario;

    @NotNull(message = "La publicación debe tener una categoría")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Transient
    @Min(value = 0, message = "La cantidad de likes no puede ser negativa")
    private Integer cantidadLikes = 0;

    @Transient
    @Min(value = 0, message = "La cantidad de dislikes no puede ser negativa")
    private Integer cantidadDislikes = 0;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("publicacion")
    private List<Comentario> comentarios = new ArrayList<>();
}
