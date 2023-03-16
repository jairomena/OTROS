package com.distribuida.servicios;

import com.distribuida.db.OrdenCompra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrdenCompraRepository implements PanacheRepository<OrdenCompra> {
}
