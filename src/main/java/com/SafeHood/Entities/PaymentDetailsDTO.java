package com.SafeHood.Entities;

public class PaymentDetailsDTO {

    private String upiId;
    private String mobileNumber;
    private String accountNumber;
    private String ifscCode;
    private String accountHolderName;

    public PaymentDetailsDTO(String upiId, String mobileNumber,
                              String accountNumber, String ifscCode,
                              String accountHolderName) {
        this.upiId = upiId;
        this.mobileNumber = mobileNumber;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.accountHolderName = accountHolderName;
    }

    // getters
    public String getUpiId() { return upiId; }
    public String getMobileNumber() { return mobileNumber; }
    public String getAccountNumber() { return accountNumber; }
    public String getIfscCode() { return ifscCode; }
    public String getAccountHolderName() { return accountHolderName; }
}