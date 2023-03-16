package com.distribuida.examen.clientes.productos;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/prductos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "producto")
public interface ProductoRestProxy {

    @GET
    @Path("/{id}")
    ProductoCliente findById(@PathParam("id") Long id);

    @GET
    List<ProductoCliente> findAll();

    @POST
    void insert(ProductoCliente productoCliente);

    @PUT
    @Path("/{id}")
    void update(ProductoCliente productoCliente, @PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);
}
