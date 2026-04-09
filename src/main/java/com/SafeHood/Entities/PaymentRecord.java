package com.SafeHood.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Payment_Record")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int paymentId;

    private double totalAmount;       // maintenance + fine
    private double maintenanceAmount;
    private double fineAmount;
  private String status  ;
    private String fineReason;

    private LocalDateTime dateTime;

    // 🔗 Mapping with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔗 Mapping with Society
    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    // 🔥 Default constructor
    public PaymentRecord() {
        super();
    }

    // 🔥 Parameterized constructor
    public PaymentRecord(int paymentId, double totalAmount, double maintenanceAmount, double fineAmount,
                         String fineReason, LocalDateTime dateTime, String status,User user, Society society) {
        super();
        this.paymentId = paymentId;
        this.totalAmount = totalAmount;
        this.maintenanceAmount = maintenanceAmount;
        this.fineAmount = fineAmount;
        this.fineReason = fineReason;
        this.dateTime = dateTime;
        this.user = user;
        this.society = society;
        this.status = status ; 
    }

    // 🔥 Getters and Setters

    public int getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(double maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getFineReason() {
        return fineReason;
    }

    public void setFineReason(String fineReason) {
        this.fineReason = fineReason;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }
}