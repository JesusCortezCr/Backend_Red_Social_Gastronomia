package com.app.backend_web.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "El usuario destino no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "usuario_destino_id", nullable = false)
    private Usuario usuarioDestino;

    @NotBlank(message = "El título de la notificación es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    private String titulo;

    @NotBlank(message = "El mensaje de la notificación es obligatorio")
    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String mensaje;

    @NotNull(message = "El tipo de notificación no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipoNotificacion;

    @NotNull(message = "El estado de lectura no puede ser nulo")
    private boolean leida = false;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}

enum TipoNotificacion {
    NUEVO_SEGUIDOR,
    COMENTARIO,
    LIKE_PUBLICACION,
    SISTEMA
}
