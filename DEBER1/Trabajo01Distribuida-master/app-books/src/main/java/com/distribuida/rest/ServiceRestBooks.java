package com.distribuida.rest;

import com.distribuida.dto.Books;
import com.distribuida.servicios.ServiceBooks;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("books")
public class ServiceRestBooks {

    @Inject
    ServiceBooks service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(){
        List<Books> list = service.getBooks();
        if(!list.isEmpty()){
            return Response.status(Response.Status.OK).entity(list).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(list).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") Integer id){
        Books book = service.getBookById(id);
        if (book.getId() != null){
            return Response.status(Response.Status.OK).entity(book).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(book).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Books book){
        boolean resp = service.createBook(book);
        if (resp){
            return Response.status(Response.Status.CREATED).entity("Libro Creado").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("Libro no Creado").build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(Books book, @PathParam("id") Integer id){
        boolean resp = service.updateBook(book, id);
        if (resp){
            return Response.status(Response.Status.CREATED).entity("Actualizado").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("No actualizado").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") Integer id){
        boolean resp = service.deleteBook(id);
        if (resp){
            return Response.status(Response.Status.OK).entity("Eliminado").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("No eliminado").build();
    }

}
