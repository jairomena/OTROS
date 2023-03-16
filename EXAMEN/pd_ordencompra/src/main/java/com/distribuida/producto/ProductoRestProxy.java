package com.distribuida.producto;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
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
    void insert(ProductoCliente obj);

    @PUT
    @Path("/{id}")
    void update(ProductoCliente obj, @PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    void delete( @PathParam("id") Long id );
}
