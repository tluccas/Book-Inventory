package com.github.tluccas.book_inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import com.github.tluccas.book_inventory.database.model.Book;
import com.github.tluccas.book_inventory.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable String id) {
        return bookService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = bookService.create(book);
        return ResponseEntity.ok(newBook);
    }

}
