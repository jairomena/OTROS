package com.distribuida.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

public class Books implements Serializable {

    @Getter @Setter private Integer id;
    @Getter @Setter private Integer authorId;
    @Getter @Setter private String isbn;
    @Getter @Setter private String title;
    @Getter @Setter private BigDecimal price;

    public Books(){}

}
