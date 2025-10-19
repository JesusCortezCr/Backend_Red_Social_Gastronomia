package com.app.backend_web.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "seguidores",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seguidor_id", "seguido_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El seguidor no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "seguidor_id", nullable = false)
    private Cliente seguidor;

    @NotNull(message = "El seguido no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "seguido_id", nullable = false)
    private Cliente seguido;

    @NotNull(message = "La fecha de seguimiento no puede ser nula")
    private LocalDateTime fechaSeguimiento = LocalDateTime.now();
}
