package com.example.demo10.webApplication.Transactions;

import com.example.demo10.webApplication.DAO.TransactionDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    TransactionDao transactionDao = new TransactionDao();


    double userBalance = 1000.0;
    @PostMapping("/create")
    public ResponseEntity<Double> createTransaction(@RequestBody TransactionRequest request, @RequestHeader("Authorization") String authorizationHeader) throws IOException, InterruptedException {
        String userToken = extractTokenFromAuthorizationHeader(authorizationHeader);
        Double balance;
        balance= transactionDao.getBalance(userToken)-request.getAmount();
        transactionDao.createTransaction(userToken,request.getAmount(),balance); ;
        return ResponseEntity.ok(userBalance);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getUserBalance(@RequestHeader("Authorization") String authorizationHeader) throws IOException, InterruptedException {
        String userToken = extractTokenFromAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(transactionDao.getBalance(userToken));
    }

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        String token = "";
        if (parts.length == 2 && "Bearer".equals(parts[0])) {
            token = parts[1];
        }
        String[] partsToken = token.split("\\|\\|");
        if (partsToken.length >= 2) {
            return partsToken[1];
        }
        return null;
    }

    @GetMapping("/getTransactions")
    public List<Transaction> getTransactions(@RequestHeader("Authorization") String authorizationHeader) throws IOException, InterruptedException {
      String userToken = extractTokenFromAuthorizationHeader(authorizationHeader);
        return transactionDao.getTransaction(userToken);
    }
}