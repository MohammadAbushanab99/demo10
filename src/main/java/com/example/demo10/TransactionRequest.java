package com.example.demo10;

public class TransactionRequest {
    private double amount;

    // Constructors, getters, and setters
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
