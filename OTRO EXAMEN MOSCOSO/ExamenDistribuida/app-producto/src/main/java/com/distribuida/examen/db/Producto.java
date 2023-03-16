package com.distribuida.examen.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producto {
    @Id
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "precio")
    private Double precio;
}
