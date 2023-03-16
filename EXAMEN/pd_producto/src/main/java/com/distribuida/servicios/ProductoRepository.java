package com.distribuida.servicios;

import com.distribuida.db.Producto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductoRepository implements PanacheRepository<Producto> {
}
