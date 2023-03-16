package com.distribuida.examen.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente {
    @Id
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String apellido;

    @Getter
    @Setter
    private String direccion;
}
