package com.distribuida.examen.rest;

import com.distribuida.examen.clientes.productos.ProductoCliente;
import com.distribuida.examen.clientes.productos.ProductoRestProxy;
import com.distribuida.examen.db.Cliente;
import com.distribuida.examen.dto.ClienteDTO;
import com.distribuida.examen.servicios.ClienteRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteRest {
    @Inject
    ClienteRepository clienteRepository;
    @RestClient
    @Inject
    ProductoRestProxy productoRestProxy;

    @GET
    @Path("/{id}")
    public Cliente findById(@PathParam("id") Long id) {
        return clienteRepository.findById(id);
    }

    @GET
    public List<Cliente> findAll() {
        return clienteRepository.findAll().list();
    }

    @GET
    @Path("/clienteProducto")
    public List<ClienteDTO> findAllWithProducto() throws Exception {
        var clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(s -> {
                    ProductoCliente productoCliente = productoRestProxy.findById(s.getId());
                    return new ClienteDTO(
                            s.getId(),
                            s.getNombre(),
                            s.getApellido(),
                            s.getDireccion(),
                            String.format("%s", productoCliente.getNombre())
                    );
                })
                .collect(Collectors.toList());
        return clientesDTO;
    }

    @POST
    public void insert(Cliente cliente) {
        clienteRepository.persist(cliente);
    }

    @PUT
    @Path("/{id}")
    public void update(Cliente cliente, @PathParam("id") Long id) {
        var clienteFind = clienteRepository.findById(id);
        clienteFind.setNombre(cliente.getNombre());
        clienteFind.setApellido(cliente.getApellido());
        clienteFind.setDireccion(cliente.getDireccion());
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        clienteRepository.deleteById(id);
    }
}
