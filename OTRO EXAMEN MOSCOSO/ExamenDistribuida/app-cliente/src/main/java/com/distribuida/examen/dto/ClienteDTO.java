package com.distribuida.examen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String nombreProducto;
}
