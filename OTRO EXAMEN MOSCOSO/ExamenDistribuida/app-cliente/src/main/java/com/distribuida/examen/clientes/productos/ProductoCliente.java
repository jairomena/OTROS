package com.distribuida.examen.clientes.productos;

import lombok.Getter;
import lombok.Setter;

public class ProductoCliente {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private Double precio;
}