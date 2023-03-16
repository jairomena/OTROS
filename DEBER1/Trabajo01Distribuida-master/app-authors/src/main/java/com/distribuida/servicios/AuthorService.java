package com.distribuida.servicios;

import com.distribuida.dto.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAuthors();
    Author getAuthorById(Integer id);
    boolean createAuthor(Author author);
    boolean updateAuthor(Author author, Integer id);
    boolean deleteAuthor(Integer id);

}
