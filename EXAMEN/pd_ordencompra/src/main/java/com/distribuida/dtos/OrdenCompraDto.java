package com.distribuida.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraDto {

    private Integer id;
    private BigDecimal clienteId;
    private BigDecimal productoId;
    private Double precio;

    private String productoNombre;
    private String clienteNombre;
}
