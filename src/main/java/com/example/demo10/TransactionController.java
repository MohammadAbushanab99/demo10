package com.example.demo10;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    double userBalance = 1000.0;
    @PostMapping("/create")
    public ResponseEntity<Double> createTransaction(@RequestBody TransactionRequest request) {
        System.out.println("request" + request.getAmount());
        this.userBalance =this.userBalance- request.getAmount();
        return  ResponseEntity.ok(userBalance);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getUserBalance() {
        // Replace this with logic to fetch the user's balance
         // Example balance
        return ResponseEntity.ok(userBalance);
    }

    @GetMapping("/getTransactions")
    public List<Transaction> getTransactions() {
        // Replace this with logic to fetch transactions from a database or other data source
        // Example:
        Transaction transaction = new Transaction();
        transaction.setName("aaa");
        transaction.setPhone("0000");
        transaction.setUserId("11111");
        transaction.setAmount(51);
        List<Transaction> transactions = new ArrayList<>();
        //transactions.add(transaction);
        return transactions;
    }
}