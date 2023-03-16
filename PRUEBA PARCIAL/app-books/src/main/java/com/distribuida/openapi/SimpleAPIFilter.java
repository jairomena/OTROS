package com.distribuida.openapi;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.info.Info;
import org.eclipse.microprofile.openapi.models.info.License;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;

import java.util.*;

public class SimpleAPIFilter implements OASFilter {

    @Override
    public APIResponse filterAPIResponse(APIResponse apiResponse) {
        if ("Falta descripcion".equals(apiResponse.getDescription())) {
            apiResponse.setDescription("Libro no encontrado ");
        }
        return apiResponse;
    }

    @Override
    public void filterOpenAPI(OpenAPI openAPI) {
        openAPI.setInfo(
                OASFactory.createObject(Info.class).title("CRUD Books App").version("1.0")
                        .description(
                                "App para realizar las operacion CRUD de Libros")
                        .license(
                                OASFactory.createObject(License.class)
                                        .name("Eclipse Public License - v 1.0").url(
                                                "https://www.eclipse.org/legal/epl-v10.html")));

        openAPI.addServer(
                OASFactory.createServer()
                        .url("http://localhost:{port}")
                        .description("Simple Open Liberty.")
                        .variables(Collections.singletonMap("port",
                                OASFactory.createServerVariable()
                                        .defaultValue("7001")
                                        .description("Server HTTP port."))));
        var tag = OASFactory.createTag().name("Books");
        List<String> tags = new ArrayList<>();
        tags.add("Books");
        openAPI.setPaths(OASFactory.createPaths()
                .addPathItem("/books", OASFactory.createPathItem()
                                                        .GET(OASFactory.createOperation()
                                                        .description("Consulta de Libros mediante su Id")
                                                        .tags(tags))));

    }

    @Override
    public Operation filterOperation(Operation operation) {
        if ("books".equals(operation.getOperationId())){
            operation.setDescription("Consulta de Libros mediante su Id");
        }
        return OASFilter.super.filterOperation(operation);
    }
}
