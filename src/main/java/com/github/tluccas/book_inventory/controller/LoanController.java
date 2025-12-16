package com.github.tluccas.book_inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import com.github.tluccas.book_inventory.database.model.Emprestimo.Loan;
import com.github.tluccas.book_inventory.dto.Loan.CreateLoanReq;
import com.github.tluccas.book_inventory.service.LoanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/emprestimos")
public class LoanController {
    
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Loan>> getAll(@RequestParam String param) {
        return ResponseEntity.ok(loanService.findAll());
    }

    @GetMapping("/{id}")
    public Loan getById(@PathVariable String id) {
        return loanService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Loan> createLoan(@RequestBody CreateLoanReq req) {
        Loan newLoan = loanService.create(req);
        return ResponseEntity.ok(newLoan);
    }
    
    
}
