package com.github.tluccas.book_inventory.service;

import org.springframework.stereotype.Service;

import com.github.tluccas.book_inventory.database.model.Emprestimo.Loan;
import com.github.tluccas.book_inventory.database.model.Emprestimo.LoanStatus;
import com.github.tluccas.book_inventory.dto.Loan.CreateLoanReq;
import com.github.tluccas.book_inventory.dto.Loan.UpdateLoanReq;
import com.github.tluccas.book_inventory.exceptions.NotFoundException;

import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import com.github.tluccas.book_inventory.database.model.Emprestimo.BookOnLoan;
import com.github.tluccas.book_inventory.database.model.User;
@Service
public class LoanService {
    
    private final BookService bookService;
    private final UserService userService;
    private final DocumentStore store;
    private static final String LOAN_PREFIX = "loans/";
    public LoanService(DocumentStore store, BookService bookService, UserService userService){
        this.store = store;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Loan create(CreateLoanReq req){
        try (IDocumentSession session = store.openSession()) {

            User user = userService.findById(req.userId());
            if (user == null) {
                throw new NotFoundException("Usuário não encontrado: " + req.userId());
            }

            List<BookOnLoan> booksToLoan = new ArrayList<>(); // Lista auxiliar para armazenar os livros válidos

            for (BookOnLoan bookOnLoan : req.books()){   
                String bookId = bookOnLoan.getBookId();

                if (bookService.findById(bookId) == null) {
                    throw new NotFoundException("Livro não encontrado: " + bookId);
                }

                booksToLoan.add(bookOnLoan);
            }
            
            Loan createdLoan = new Loan();

            createdLoan.setUserId(user.getId());
            createdLoan.setBooks(booksToLoan);
            createdLoan.setLoanDate(LocalDate.now());
            createdLoan.setReturnDate(req.returnDate());
            createdLoan.setStatus(LoanStatus.DEVOLUCAO_PENDENTE);
            session.store(createdLoan);
            session.saveChanges();
            return createdLoan;
        }
    }

    public List<Loan> findAll(){
        try (IDocumentSession session = store.openSession()) {
            return session.query(Loan.class).toList(); 
        }
    }

    public Loan findById(String id){
        String fullId = LOAN_PREFIX + id;

        try (IDocumentSession session = store.openSession()) {
            return session.load(Loan.class, fullId);
        }
    }

    public Loan update(String id, UpdateLoanReq data) {
        String fullId = LOAN_PREFIX + id;

        try (IDocumentSession session = store.openSession()) {
            Loan existingLoan = session.load(Loan.class, fullId);
            if (existingLoan == null) {
                throw new NotFoundException("Empréstimo não encontrado: " + id);
            }

            existingLoan.setStatus(LoanStatus.valueOf(data.status()));
            session.saveChanges();
            return existingLoan;
        }
    }

    public void delete(String id) {
        String fullId = LOAN_PREFIX + id;

        try(IDocumentSession session = store.openSession()) {
            Loan existingLoan = session.load(Loan.class, fullId);
            if (existingLoan == null) {
                throw new NotFoundException("Empréstimo não encontrado: " + id);
            }

            session.delete(existingLoan);
            session.saveChanges();
        }
    }
    
}
