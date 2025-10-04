package com.app.backend_web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

    @Column(nullable = false)
    private String contenido;

    private boolean estadoComentario=true;
    private Integer cantidadReportes=0;
    private LocalDateTime fechaCreacion=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    @JsonIgnore
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    @OneToMany(mappedBy = "comentarioPadre")
    @JsonIgnoreProperties("comentarioPadre") 
    private List<Comentario> respuestas=new ArrayList<>();

    @Transient
    private Integer cantidadRespuestas;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"publicaciones", "comentarios", "password", "seguidores", "siguiendo"})
    private Usuario usuario;

    @PostLoad
    private void calcularRespuestas() {
        this.cantidadRespuestas = respuestas != null ? respuestas.size() : 0;
    }
}
