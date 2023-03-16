package com.distribuida.main;

import com.distribuida.config.ThymeleafTemplateEngine;
import com.distribuida.dto.Author;
import com.distribuida.dto.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import spark.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ClienteMain {

    public static void main(String[] args) {
        port(9999);
        System.out.println("Aplicacion arrancada");

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("tituloIndex", "Aplicacion cliente para gestionar Autores y Libros");
            return new ModelAndView(model, "index");
        }, new ThymeleafTemplateEngine());

        get("/authors", (req, res) -> {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://traefik/authors");
            var response = httpclient.execute(request);

            HttpEntity entity = response.getEntity();
            String resultado = EntityUtils.toString(entity);

            ObjectMapper mapper = new ObjectMapper();
            List<Author> listAuthors = mapper.readValue(resultado, new TypeReference<List<Author>>(){});

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Lista de Autores");
            model.put("lista", listAuthors);
            return new ModelAndView(model, "autores");
        }, new ThymeleafTemplateEngine());

        get("/authors/edit/:id", (req, res) -> {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://traefik/authors/" + req.params("id"));
            var response = httpclient.execute(request);

            HttpEntity entity = response.getEntity();
            String resultado = EntityUtils.toString(entity);

            ObjectMapper mapper = new ObjectMapper();
            Author author = mapper.readValue(resultado, Author.class);

            Map<String, Object> model = new HashMap<>();
            model.put("author", author);

            return new ModelAndView(model, "editar");
        }, new ThymeleafTemplateEngine());

        post("/authors/edit/sendEdit", (req, res) -> {
            HashMap<String, String> mapDatos = new HashMap<>();
            String [] datos = req.body().split("\r\n");
            for (String d : datos){
                String [] campos = d.split("=");
                mapDatos.put(campos[0], campos[1]);
            }
            Author author = new Author();
            author.setId(Integer.parseInt(mapDatos.get("idAutor")));
            author.setFirstName(mapDatos.get("nombreAutor"));
            author.setLastName(mapDatos.get("apellidoAutor"));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(author);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPut request = new HttpPut("http://traefik/authors/" + author.getId());
            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            var response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == 201){
                res.redirect("/authors");
            }
            return null;
        });

        get("/authors/delete/:id", (req, res) -> {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpDelete request = new HttpDelete("http://traefik/authors/" + req.params("id"));
            var response = httpclient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200){
                res.redirect("/authors");
            }
            return null;
        });

        get("/authors/create", (req, res) -> {

            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "crearAutor");
        }, new ThymeleafTemplateEngine());

        post("/authors/create/send", (req, res) -> {
            HashMap<String, String> mapDatos = new HashMap<>();
            String [] datos = req.body().split("\r\n");
            for (String d : datos){
                String [] campos = d.split("=");
                mapDatos.put(campos[0], campos[1]);
            }
            Author author = new Author();
            author.setFirstName(mapDatos.get("nombreAutor"));
            author.setLastName(mapDatos.get("apellidoAutor"));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(author);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost request = new HttpPost("http://traefik/authors/");
            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            var response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == 201){
                res.redirect("/authors");
            }
            return null;
        });

        /*
         * METODOS GESTION LIBROS
         */
        get("/books", (req, res) -> {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://traefik/books");
            var response = httpclient.execute(request);

            HttpEntity entity = response.getEntity();
            String resultado = EntityUtils.toString(entity);

            ObjectMapper mapper = new ObjectMapper();
            List<Book> bookList = mapper.readValue(resultado, new TypeReference<List<Book>>(){});

            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Lista de Libros");
            model.put("lista", bookList);
            return new ModelAndView(model, "libros");
        }, new ThymeleafTemplateEngine());

        get("/books/edit/:id", (req, res) -> {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://traefik/books/" + req.params("id"));
            var response = httpclient.execute(request);

            HttpEntity entity = response.getEntity();
            String resultado = EntityUtils.toString(entity);

            ObjectMapper mapper = new ObjectMapper();
            Book book = mapper.readValue(resultado, Book.class);

            Map<String, Object> model = new HashMap<>();
            model.put("book", book);

            return new ModelAndView(model, "editarLibro");
        }, new ThymeleafTemplateEngine());

        post("/books/edit/sendEdit", (req, res) -> {
            HashMap<String, String> mapDatos = new HashMap<>();
            String [] datos = req.body().split("\r\n");
            for (String d : datos){
                String [] campos = d.split("=");
                mapDatos.put(campos[0], campos[1]);
            }
            Book book = new Book();
            book.setId(Integer.parseInt(mapDatos.get("idLibro")));
            book.setAuthorId(Integer.parseInt(mapDatos.get("autorId")));
            book.setIsbn(mapDatos.get("isbn"));
            book.setTitle(mapDatos.get("tituloLibro"));
            book.setPrice(new BigDecimal(mapDatos.get("precioLibro")));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(book);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPut request = new HttpPut("http://traefik/books/" + book.getId());
            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            var response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == 201){
                res.redirect("/books");
            }
            return null;
        });

        get("/books/delete/:id", (req, res) -> {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpDelete request = new HttpDelete("http://traefik/books/" + req.params("id"));
            var response = httpclient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200){
                res.redirect("/books");
            }
            return null;
        });

        get("/books/create", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "crearLibro");
        }, new ThymeleafTemplateEngine());

        post("/books/create/send", (req, res) -> {
            HashMap<String, String> mapDatos = new HashMap<>();
            String [] datos = req.body().split("\r\n");
            for (String d : datos){
                String [] campos = d.split("=");
                mapDatos.put(campos[0], campos[1]);
            }
            Book book = new Book();
            book.setAuthorId(Integer.parseInt(mapDatos.get("autorId")));
            book.setIsbn(mapDatos.get("isbn"));
            book.setTitle(mapDatos.get("tituloLibro"));
            book.setPrice(new BigDecimal(mapDatos.get("precioLibro")));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(book);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost request = new HttpPost("http://traefik/books/");
            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            var response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == 201){
                res.redirect("/books");
            }
            return null;
        });


    }

}
