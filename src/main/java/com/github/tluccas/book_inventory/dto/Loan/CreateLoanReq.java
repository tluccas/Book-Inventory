package com.github.tluccas.book_inventory.dto.Loan;
import com.github.tluccas.book_inventory.database.model.Emprestimo.BookOnLoan;
import java.util.List;
import java.time.LocalDate;
public record CreateLoanReq( String userId, 
                             List<BookOnLoan> books,
                             LocalDate returnDate
                              ) {}
