package com.distribuida.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente {
    @Id
    @Getter @Setter private Integer id;

    @Column(name = "nombre")
    @Getter @Setter private String nombre;

    @Column(name = "apellido")
    @Getter @Setter private String apellido;

    @Column(name = "direccion")
    @Getter @Setter private String direccion;
}
