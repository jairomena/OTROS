package com.distribuida.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class OrdenCompra {
    @Id
    @Getter @Setter private Integer id;

    @Column(name = "cliente_id")
    @Getter @Setter private BigDecimal clienteId;

    @Column(name = "producto_id")
    @Getter @Setter private BigDecimal productoId;

    @Column(name = "precio")
    @Getter @Setter private Double precio;
}
