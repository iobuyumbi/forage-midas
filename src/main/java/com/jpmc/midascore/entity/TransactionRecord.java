package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserRecord recipient;

    private float amount;

    private double incentiveAmount; // Keep this field

    // Default constructor
    protected TransactionRecord() {
    }

    // Update this constructor to include incentiveAmount
    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, double incentiveAmount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentiveAmount = incentiveAmount; // Set incentive amount here
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public float getAmount() {
        return amount;
    }

    public double getIncentiveAmount() {
        return incentiveAmount;
    }

    // Optional: Override toString for better logging
    @Override
    public String toString() {
        return "TransactionRecord{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", amount=" + amount +
                ", incentiveAmount=" + incentiveAmount +
                '}';
    }
}
