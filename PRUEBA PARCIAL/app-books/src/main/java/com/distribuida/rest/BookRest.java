package com.distribuida.rest;

import com.distribuida.clientes.authors.AuthorRestProxy;
import com.distribuida.clientes.authors.AuthorsCliente;
import com.distribuida.db.Book;
import com.distribuida.dtos.BookDto;
import com.distribuida.servicios.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookRest {

    @Inject BookRepository bookService;

    @RestClient
    @Inject AuthorRestProxy proxyAuthor;

    /**
     * GET          /books/{id}     buscar un libro por ID
     * GET          /books          listar todos
     * POST         /books          insertar
     * PUT/PATCH    /books/{id}     actualizar
     * DELETE       /books/{id}     eliminar
     */

    @GET
    @Path("/{id}")
    @Operation(summary = "Devuelve un Libro mediante su Id")
    @APIResponse(
            description = "Libro Encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Book.class)),
            responseCode = "200")
    @APIResponse(
            responseCode = "404",
            description = "Libro no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = String.class, example = "Book[id=%d] not found.")))
    public Response findById(@Parameter(description = "Id del Libro a buscar", required = true)
                                 @PathParam("id") Integer id) {
        Book ret = bookService.findById(id);

        if( ret != null ) {
            return Response.ok(ret).build();
        }
        else {
            String msg = String.format( "Book[id=%d] not found.", id );

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(msg)
                    .build();
        }
    }

    @GET
    @Operation(summary = "Devuelve una lista de Libros de la base de datos")
    @APIResponse(
            description = "Lista Libros Encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)),
            responseCode = "200")
    @APIResponse(
            responseCode = "404",
            description = "Lista Libros no encontrada")
    public List<Book> findAll() {
        System.out.println("Buscando todos");
        return bookService.findAll();
    }

    @GET
    @Path("/all")
    @Operation(summary = "Devuelve una lista de Libros de la base de datos incluido los nombres y apellidos del Autor")
    @APIResponse(
            description = "Lista Libros Encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = BookDto.class, type = SchemaType.ARRAY)),
            responseCode = "200")
    @APIResponse(
            responseCode = "404",
            description = "Lista Libros no encontrada")
    public List<BookDto> findAllCompleto() throws Exception {
        var books = bookService.findAll();

//        AuthorRestProxy proxyAuthor = RestClientBuilder.newBuilder()
//                .baseUrl( new URL("http://127.0.0.1:8080") )
//                .connectTimeout( 1000, TimeUnit.MILLISECONDS )
//                .build( AuthorRestProxy.class );

        List<BookDto> ret = books.stream()
                .map(s -> {
                    System.out.println("*********buscando " + s.getId() );

                    AuthorsCliente author = proxyAuthor.findById(s.getId().longValue());
                    return new BookDto(
                            s.getId(),
                            s.getIsbn(),
                            s.getTitle(),
                            s.getAuthor(),
                            s.getPrice(),
                            String.format("%s, %s", author.getLastName(), author.getFirtName())
                    );
                })
                .collect(Collectors.toList());

        return ret;
    }

    @POST
    @Operation(summary = "Crear Libro", description = "Permite crear un Libro")
    public void insert(@RequestBody(description = "Objeto Autor a crearse",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Book.class))) Book book) {
        bookService.insert(book);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar Libro", description = "Permite actualizar un Libro mediante su Id")
    public void update(@RequestBody(description = "Objeto Autor a actualizarse",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Book.class))) Book book,
                       @Parameter(description = "Id del Libro que se va a actualizar", required = true)
                       @PathParam("id") Integer id) {
        book.setId(id);
        bookService.update(book);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Elimina Libro", description = "Permite eliminar un Libro mediante su Id")
    public void delete(@Parameter(description = "Id del Libro que se va a eliminar", required = true)
                           @PathParam("id") Integer id ) {
        bookService.delete(id);
    }
}
