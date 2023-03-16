package com.distribuida.rest;

import com.distribuida.db.Producto;
import com.distribuida.servicios.ProductoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoRest {

    @GET
    @Path("/{id}")
    public Producto findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @Inject
    ProductoRepository repository;

    @GET
    public List<Producto> findAll() {
        return repository
                .findAll()
                .list();
    }

    @POST
    public void insert(Producto obj) {
        repository.persist(obj);
    }

    @PUT
    @Path("/{id}")
    public void update(Producto obj, @PathParam("id") Long id) {

        var client = repository.findById(id);

        client.setNombre(obj.getNombre());
        client.setPrecio(obj.getPrecio());
    }

    @DELETE
    @Path("/{id}")
    public void delete( @PathParam("id") Long id ) {
        repository.deleteById(id);
    }
}
