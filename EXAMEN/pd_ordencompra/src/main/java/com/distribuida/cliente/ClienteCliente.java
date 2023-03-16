package com.distribuida.cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@ToString
public class ClienteCliente {
    @Getter @Setter private Integer id;
    @Getter @Setter private String nombre;
    @Getter @Setter private String apellido;
    @Getter @Setter private String direccion;


}
