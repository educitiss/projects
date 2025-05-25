package com.example.expensetracker;

public class Expense {
    private double amount;
    private String description;
    private String category;
    private String date; // format: yyyy-MM-dd

    public Expense(double amount, String description, String category, String date) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }
}
