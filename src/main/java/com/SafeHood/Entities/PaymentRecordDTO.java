package com.SafeHood.Entities;
import java.time.LocalDateTime;

public class PaymentRecordDTO {

    private int paymentId;
    private double totalAmount;
    private double maintenanceAmount;
    private double fineAmount;
    private String status;
    private String fineReason;
    private LocalDateTime dateTime;

    public PaymentRecordDTO(int paymentId, double totalAmount,
                            double maintenanceAmount, double fineAmount,
                            String status, String fineReason,
                            LocalDateTime dateTime) {

        this.paymentId = paymentId;
        this.totalAmount = totalAmount;
        this.maintenanceAmount = maintenanceAmount;
        this.fineAmount = fineAmount;
        this.status = status;
        this.fineReason = fineReason;
        this.dateTime = dateTime;
    }

    // getters
    public int getPaymentId() { return paymentId; }
    public double getTotalAmount() { return totalAmount; }
    public double getMaintenanceAmount() { return maintenanceAmount; }
    public double getFineAmount() { return fineAmount; }
    public String getStatus() { return status; }
    public String getFineReason() { return fineReason; }
    public LocalDateTime getDateTime() { return dateTime; }
}
