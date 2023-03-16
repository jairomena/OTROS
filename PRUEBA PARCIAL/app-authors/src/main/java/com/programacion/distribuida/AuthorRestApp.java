package com.programacion.distribuida;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="Documentacion Operaciones CRUD de Autores",
                version = "1.0.0",
                description = "Recursos que permiten realizar todas la operacion CRUD para Autores"
        )
)
@ApplicationPath("/")
public class AuthorRestApp extends Application {
}
