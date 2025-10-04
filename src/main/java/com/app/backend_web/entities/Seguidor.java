package com.app.backend_web.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "seguidores",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seguidor_id","seguido_id"})
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

    @ManyToOne
    @JoinColumn(name = "seguidor_id")
    private Cliente seguidor;

    @ManyToOne
    @JoinColumn(name = "seguido_id")
    private Cliente seguido;

    private LocalDateTime fechaSeguimiento = LocalDateTime.now();

}
