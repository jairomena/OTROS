package com.distribuida.servicios;

import com.distribuida.dto.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AuthorServiceImpl implements AuthorService, PanacheRepositoryBase<Author, Integer> {

    @Override
    public List<Author> getAuthors() {
        List<Author> listAuthors = new ArrayList<>();
        listAuthors = findAll().list();
        return listAuthors;
    }

    @Override
    public Author getAuthorById(Integer id) {
        return findById(id);
    }

    @Override
    @Transactional
    public boolean createAuthor(Author author) {
        if (author != null){
            persist(author);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateAuthor(Author author, Integer id) {
        Author auth = findById(id);
        if(auth != null){
            auth.setFirstName(author.getFirstName());
            auth.setLastName(author.getLastName());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteAuthor(Integer id) {
        Author auth = findById(id);
        if(auth != null){
            delete(auth);
            return true;
        }
        return false;
    }
}
