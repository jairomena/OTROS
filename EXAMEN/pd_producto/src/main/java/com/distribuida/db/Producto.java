package com.distribuida.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producto {
    @Id
    @Getter @Setter private Integer id;

    @Column(name = "nombre")
    @Getter @Setter private String nombre;

    @Column(name = "precio")
    @Getter @Setter private Double precio;
}
