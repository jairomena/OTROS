package com.distribuida.servicios;

import com.distribuida.dto.Books;
import io.helidon.common.reactive.Single;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ServiceBooksImpl implements ServiceBooks{

    @Inject private DbClient dbClient;

    @Override
    public Books getBookById(Integer id) {
        Books book = new Books();
        try {
            String query = "select * from books where id = ?";
            Single<List<DbRow>> s = dbClient.execute(exec -> exec
                    .createQuery(query)
                    .params(id)
                    .execute()).collectList();
            List<DbRow> rs = s.get();
            if (!rs.isEmpty()){
                for (DbRow r : rs) {
                    book.setId((int) r.column("id").value());
                    book.setAuthorId((int) r.column("author_id").value());
                    book.setIsbn((String)r.column("isbn").value());
                    book.setTitle((String)r.column("title").value());
                    book.setPrice((BigDecimal)r.column("price").value());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Books> getBooks() {
        List<Books> listBooks = new ArrayList<>();
        try{
            String query = "select * from books";
            Single<List<DbRow>> s = dbClient.execute(exec -> exec
                    .createQuery(query)
                    .execute()).collectList();
            List<DbRow> rs = s.get();
            if (!rs.isEmpty()){
                for (DbRow r : rs) {
                    Books book = new Books();
                    book.setId((int) r.column("id").value());
                    book.setAuthorId((int) r.column("author_id").value());
                    book.setIsbn((String)r.column("isbn").value());
                    book.setTitle((String)r.column("title").value());
                    book.setPrice((BigDecimal)r.column("price").value());
                    listBooks.add(book);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listBooks;
    }

    @Override
    public boolean createBook(Books book) {
        boolean resp = false;
        Long filas = Long.valueOf(0);
        try {
            String query = "insert into books(author_id, isbn, title, price) values(?, ?, ?, ?)";
            Single<Long> s = dbClient.execute(exec -> exec
                    .createInsert(query)
                    .params(book.getAuthorId(), book.getIsbn(), book.getTitle(), book.getPrice())
                    .execute());
            filas = s.get();
            resp = filas.intValue() > 0;

        }catch (Exception e){
            e.printStackTrace();
        }

        return resp;
    }

    @Override
    public boolean updateBook(Books book, Integer id) {
        boolean resp = false;
        Long filas = Long.valueOf(0);
        try{
            String query = "update books set author_id = ?, isbn = ?, title = ?, price = ? where id = ?";
            Single<Long> s = dbClient.execute(exec -> exec
                    .createUpdate(query)
                    .params(book.getAuthorId(), book.getIsbn(), book.getTitle(), book.getPrice(), id)
                    .execute());
            filas = s.get();
            resp = filas.intValue() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public boolean deleteBook(Integer id) {
        boolean resp = false;
        Long filas = Long.valueOf(0);
        try{
            String query = "delete from books where id = ?";
            Single<Long> s = dbClient.execute(exec -> exec
                    .createDelete(query)
                    .params(id)
                    .execute());
            filas = s.get();
            resp = filas.intValue() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }
}
