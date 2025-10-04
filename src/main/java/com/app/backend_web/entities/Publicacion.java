package com.app.backend_web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //si
    private String titulo; //si
    private String descripcion; //si
    private String contenido; //si
    private String imagen;
    private boolean estado = true; //si

    private Integer calificacion; //si

    private LocalDateTime fechaCreacion = LocalDateTime.now(); //si

    private String archivo; //si

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; //si

    // desayuno almuerco cena
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria; //si

    private Integer cantidadLikes=0; //si

    @OneToMany(mappedBy = "publicacion",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios=new ArrayList<>(); //si

    private Integer cantidadDislikes=0; //si

    private LocalDateTime fechaActualizacion; //si
}
