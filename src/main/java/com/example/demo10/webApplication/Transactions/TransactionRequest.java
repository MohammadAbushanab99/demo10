package com.example.demo10.webApplication.Transactions;

public class TransactionRequest {
    private double amount;

    public TransactionRequest() {}

    public TransactionRequest(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
