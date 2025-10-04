package com.app.backend_web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    private Long id; //si
    private String titulo; //si
    private String descripcion; //si
    private String contenido; //si
    private String imagenUrl;
    private boolean estado = true; //si

    private Integer calificacion; //si

    private LocalDateTime fechaCreacion = LocalDateTime.now(); //si

    private LocalDateTime fechaActualizacion; //si
    private String archivo; //si

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; //si

    // desayuno almuerco cena
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria; //si

    @Transient
    private Integer cantidadLikes = 0;

    @Transient
    private Integer cantidadDislikes = 0;


    @OneToMany(mappedBy = "publicacion",cascade = CascadeType.ALL)
    private List<Comentario> comentarios=new ArrayList<>(); //si


}
