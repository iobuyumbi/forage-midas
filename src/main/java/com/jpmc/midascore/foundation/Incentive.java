package com.jpmc.midascore.foundation;

public class Incentive {

    private double amount;

    // Default constructor
    public Incentive() {
    }

    // Constructor with amount
    public Incentive(double amount) {
        this.amount = amount;
    }

    // Getter and setter
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        // Optional: Add validation to prevent negative amounts
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Incentive{amount=" + amount + "}";
    }
}
