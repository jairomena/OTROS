package com.distribuida.cliente;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "cliente")
public interface ClienteRestProxy {

    @GET
    @Path("/{id}")
    ClienteCliente findById(@PathParam("id") Long id);

    @GET
    List<ClienteCliente> findAll();

    @POST
    void insert(ClienteCliente obj);

    @PUT
    @Path("/{id}")
    void update(ClienteCliente obj, @PathParam("id") Long id);

    @DELETE
    @Path("/{id}")
    void delete( @PathParam("id") Long id );
}
