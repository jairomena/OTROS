package com.distribuida.producto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

@ToString
public class ProductoCliente {
    @Getter @Setter private Integer id;
    @Getter @Setter private String nombre;
    @Getter @Setter private Double precio;
}
