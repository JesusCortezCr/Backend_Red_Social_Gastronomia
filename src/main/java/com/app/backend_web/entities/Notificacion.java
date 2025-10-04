package com.app.backend_web.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notificaciones")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_destino_id")
    private Usuario usuarioDestino;
    private String titulo;
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private TipoNotificacion  tipoNotificacion;
    private boolean leida = false;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

}

enum TipoNotificacion {
    NUEVO_SEGUIDOR,
    COMENTARIO,
    LIKE_PUBLICACION,
    SISTEMA
}
