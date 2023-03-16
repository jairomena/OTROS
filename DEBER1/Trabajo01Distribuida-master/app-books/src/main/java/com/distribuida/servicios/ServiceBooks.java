package com.distribuida.servicios;

import com.distribuida.dto.Books;

import java.util.List;

public interface ServiceBooks {

    Books getBookById(Integer id);
    List<Books> getBooks();
    boolean createBook(Books book);
    boolean updateBook(Books book, Integer id);
    boolean deleteBook(Integer id);

}
