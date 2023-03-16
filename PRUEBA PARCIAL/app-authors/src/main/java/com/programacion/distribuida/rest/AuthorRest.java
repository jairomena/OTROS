package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Authors;
import com.programacion.distribuida.servicios.AuthorRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @Inject AuthorRepository repository;

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtiene un Autor por su Id")
    @APIResponses({@APIResponse(responseCode = "404", description = "Autor no encontrado"),
                   @APIResponse(responseCode = "200",description = "Autor Encontrado",
                                content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                                   schema = @Schema(implementation = Authors.class)))}
    )
    public Authors findById(@PathParam("id") Integer id) {
        return repository.findById(id);
    }

    @GET
    @Operation(summary = "Obtiene una lista con todos los Autores de la base de datos")
    @APIResponses({@APIResponse(responseCode = "404", description = "Lista de Autores no encontrada"),
            @APIResponse(responseCode = "200",description = "Lista de Autores encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Authors.class, type = SchemaType.ARRAY)))}
    )
    public List<Authors> findAll() {
        return repository
                .findAll()
                .list();
    }

    @POST
    @Operation(summary = "Crear Autor", description = "Permite crear un Autor")
    public void insert(@RequestBody(description = "Objeto Autor a crearse",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Authors.class))) Authors obj) {
        repository.persist(obj);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar Autor", description = "Permite actualizar un Autor mediante su Id")
    public void update(@RequestBody(description = "Objeto Autor a actualizarse",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Authors.class))) Authors obj,
                       @Parameter(description = "Id del Autor que se va a actualizar", required = true)
                       @PathParam("id") Integer id) {

        var author = repository.findById(id);

        author.setFirtName(obj.getFirtName());
        author.setLastName(obj.getLastName());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Elimina Autor", description = "Permite eliminar un Autor mediante su Id")
    public void delete(@Parameter(description = "Id del Autor que se va a eliminar", required = true)
                           @PathParam("id") Integer id ) {
        repository.deleteById(id);
    }
}
