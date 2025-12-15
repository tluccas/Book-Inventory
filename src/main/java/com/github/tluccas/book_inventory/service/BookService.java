package com.github.tluccas.book_inventory.service;

import org.springframework.stereotype.Service;

import com.github.tluccas.book_inventory.database.model.Book;
import com.github.tluccas.book_inventory.exceptions.NotFoundException;

import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;

import java.util.List;

@Service
public class BookService {
    
    private final DocumentStore store;

    public BookService(DocumentStore store){
        this.store = store;
    }

    public Book create(Book book){
        try (IDocumentSession session = store.openSession()) {
            session.store(book);
            session.saveChanges();
            return book;
        }
    }

    public List<Book> findAll(){
        try (IDocumentSession session = store.openSession()) {
            return session.query(Book.class).toList();
        }
    }

    public Book findById(String id){
        try (IDocumentSession session = store.openSession()) {
            return session.load(Book.class, id);
        }
    }

    public Book update(String id, Book data) {
        try (IDocumentSession session = store.openSession()) {
            Book existingBook = session.load(Book.class, id);
            if (existingBook == null) {
                throw new NotFoundException("Livro não encontrado: " + id);
            }

            existingBook.setTitle(data.getTitle());
            existingBook.setAuthors(data.getAuthors());
            existingBook.setCategories(data.getCategories());

            session.saveChanges();
            return existingBook;
        } 
    }

    public void delete(String id) {
        try(IDocumentSession session = store.openSession()) {
            Book existingBook = session.load(Book.class, id);
            if (existingBook == null) {
                throw new NotFoundException("Livro não encontrado: " + id);
            }

            session.delete(existingBook);
            session.saveChanges();
        }
    }
}
