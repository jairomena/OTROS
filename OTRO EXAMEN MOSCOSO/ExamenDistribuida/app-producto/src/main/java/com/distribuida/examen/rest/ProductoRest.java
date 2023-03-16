package com.distribuida.examen.rest;

import com.distribuida.examen.db.Producto;
import com.distribuida.examen.servicios.ProductoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoRest {

    @Inject
    ProductoRepository repository;

    @GET
    @Path("/{id}")
    public Producto findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @GET
    public List<Producto> findAll() {
        return repository.findAll().list();
    }

    @POST
    public void insert(Producto producto) {
        repository.persist(producto);
    }

    @PUT
    @Path("/{id}")
    public void update(Producto producto, @PathParam("id") Long id) {
        var productoFind = repository.findById(id);
        productoFind.setNombre(producto.getNombre());
        productoFind.setPrecio(producto.getPrecio());
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        repository.deleteById(id);
    }
}
