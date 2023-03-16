package com.distribuida.rest;

import com.distribuida.dto.Author;
import com.distribuida.servicios.AuthorService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("authors")
public class ServiceRestAuthor {

    @Inject
    AuthorService serviceAuthor;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthors(){
        List<Author> authorList = serviceAuthor.getAuthors();
        if (!authorList.isEmpty()){
            return Response.status(Response.Status.OK).entity(authorList).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("No existen Autores").build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") Integer id){
        Author author = serviceAuthor.getAuthorById(id);
        if (author != null){
            return Response.status(Response.Status.OK).entity(author).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("No existe Autor").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author){
        boolean resp = serviceAuthor.createAuthor(author);
        if(resp){
            return Response.status(Response.Status.CREATED).entity(author).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("No se pudo crear Autor").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(Author author, @PathParam("id") Integer id){
        boolean resp = serviceAuthor.updateAuthor(author, id);
        if(resp){
            return Response.status(Response.Status.CREATED).entity("Actualizado").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("No se pudo actualizar").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAuthor(@PathParam("id") Integer id){
        boolean resp = serviceAuthor.deleteAuthor(id);
        if(resp){
            return Response.status(Response.Status.OK).entity("Actualizado").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("No se pudo eliminar").build();
    }

}
