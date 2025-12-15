package com.github.tluccas.book_inventory.service;

import org.springframework.stereotype.Service;

import com.github.tluccas.book_inventory.database.model.User;
import com.github.tluccas.book_inventory.exceptions.NotFoundException;

import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;

import java.util.List;

@Service
public class UserService {
    
    private final DocumentStore store;

    public UserService(DocumentStore store){
        this.store = store;
    }

    public User create(User user){
        try (IDocumentSession session = store.openSession()) {
            session.store(user);
            session.saveChanges();
            return user;
        }
    }

    public List<User> findAll(){
        try (IDocumentSession session = store.openSession()) {
            return session.query(User.class).toList();
        }
    }

    public User findById(String id){
        try (IDocumentSession session = store.openSession()) {
            return session.load(User.class, id);
        }
    }

    public User update(String id, User data) {
        try (IDocumentSession session = store.openSession()) {
            User existingUser = session.load(User.class, id);
            if (existingUser == null) {
                throw new NotFoundException("Usuário não encontrado: " + id);
            }

            existingUser.setUsername(data.getUsername());
            existingUser.setEmail(data.getEmail());

            session.saveChanges();
            return existingUser;
        } 
    }

    public void delete(String id) {
        try(IDocumentSession session = store.openSession()) {
            User existingUser = session.load(User.class, id);
            if (existingUser == null) {
                throw new NotFoundException("Usuário não encontrado: " + id);
            }

            session.delete(existingUser);
            session.saveChanges();
        }
    }}
