package com.distribuida.rest;

import com.distribuida.db.Cliente;
import com.distribuida.servicios.ClienteRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteRest {

    @GET
    @Path("/{id}")
    public Cliente findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @Inject
    ClienteRepository repository;

    @GET
    public List<Cliente> findAll() {
        return repository
                .findAll()
                .list();
    }

    @POST
    public void insert(Cliente obj) {
        repository.persist(obj);
    }

    @PUT
    @Path("/{id}")
    public void update(Cliente obj, @PathParam("id") Long id) {

        var client = repository.findById(id);

        client.setNombre(obj.getNombre());
        client.setApellido(obj.getApellido());
        client.setDireccion(obj.getDireccion());
    }

    @DELETE
    @Path("/{id}")
    public void delete( @PathParam("id") Long id ) {
        repository.deleteById(id);
    }
}
