package com.distribuida.rest;

import com.distribuida.cliente.ClienteCliente;
import com.distribuida.cliente.ClienteRestProxy;
import com.distribuida.db.OrdenCompra;
import com.distribuida.dtos.OrdenCompraDto;
import com.distribuida.producto.ProductoCliente;
import com.distribuida.producto.ProductoRestProxy;
import com.distribuida.servicios.OrdenCompraRepository;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/purchaseorder")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdenCompraRest {

    @GET
    @Path("/{id}")
    public OrdenCompra findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @Inject
    OrdenCompraRepository repository;

    @RestClient
    @Inject
    ClienteRestProxy proxyCliente;

    @RestClient
    @Inject
    ProductoRestProxy proxyProducto;

    @GET
    public List<OrdenCompra> findAll() {
        return repository
                .findAll()
                .list();
    }

    @GET
    @Path("/all")
    @Retry(delay = 1000)
    @Fallback(fallbackMethod = "findAllFallback")
    public List<OrdenCompraDto> findAllCompleto() {
        var oCompras = repository.findAll();

        List<OrdenCompraDto> oCompraClientes = oCompras.stream()
                .map(s -> {
                    System.out.println("********* buscando " + s.getId());

                    ClienteCliente cliente = proxyCliente.findById(s.getId().longValue());
                    return new OrdenCompraDto(
                            s.getId(),
                            s.getClienteId(),
                            s.getProductoId(),
                            s.getPrecio(),
                            "Pendiente",
                            String.format("%s, %s", cliente.getNombre(), cliente.getApellido())
                    );
                })
                .collect(Collectors.toList());

        oCompraClientes = oCompras.stream()
                .map(s -> {
                    System.out.println("********* buscando " + s.getId());

                    ClienteCliente cliente = proxyCliente.findById(s.getId().longValue());
                    return new OrdenCompraDto(
                            s.getId(),
                            s.getClienteId(),
                            s.getProductoId(),
                            s.getPrecio(),
                            "Pendiente",
                            String.format("%s, %s", cliente.getNombre(), cliente.getApellido())
                    );
                })
                .collect(Collectors.toList());

        List<OrdenCompraDto> oCompraClientesProd = oCompraClientes.stream()
                .map(s -> {
                    System.out.println("********* buscando " + s.getId());

                    ProductoCliente producto = proxyProducto.findById(s.getId().longValue());
                    return new OrdenCompraDto(
                            s.getId(),
                            s.getClienteId(),
                            s.getProductoId(),
                            s.getPrecio(),
                            String.format("%s", producto.getNombre()),
                            s.getClienteNombre()
                    );
                })
                .collect(Collectors.toList());

        if (oCompraClientes.size() == 0) {
            throw new RuntimeException("No se encontraron ordenes de compra");
        }

        return oCompraClientes;
    }

    @POST
    public void insert(OrdenCompra obj) {
        repository.persist(obj);
    }

    @PUT
    @Path("/{id}")
    public void update(OrdenCompra obj, @PathParam("id") Long id) {

        var client = repository.findById(id);

        client.setClienteId(obj.getClienteId());
        client.setProductoId(obj.getProductoId());
        client.setPrecio(obj.getPrecio());
    }

    @DELETE
    @Path("/{id}")
    public void delete( @PathParam("id") Long id ) {
        repository.deleteById(id);
    }

    public List<OrdenCompraDto> findAllFallback() {
        // generate list of orders with the column client and product as "Unknown"
        var books = repository.findAll();

        return books.stream()
                .map(s -> new OrdenCompraDto(
                        s.getId(),
                        s.getClienteId(),
                        s.getProductoId(),
                        s.getPrecio(),
                        "Unknown",
                        "Unknown"

                ))
                .toList();
    }
}
