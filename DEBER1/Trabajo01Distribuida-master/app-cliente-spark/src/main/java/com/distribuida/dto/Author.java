package com.distribuida.dto;

import lombok.Getter;
import lombok.Setter;

public class Author {

    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    public Author(){}

}
