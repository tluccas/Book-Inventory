package com.github.tluccas.book_inventory.database.model.Emprestimo;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class Loan {
    
    private String id;
    private String userId;

    private List<BookOnLoan> books;

    private LocalDate loanDate;
    private LocalDate returnDate;

    private LoanStatus status;
}
