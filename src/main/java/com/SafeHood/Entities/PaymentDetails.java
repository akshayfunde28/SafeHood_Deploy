package com.SafeHood.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Payment_Details")
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int paymentDetailsId;

    // UPI
    private String upiId;
    private String mobileNumber;

    // Bank
    private String accountNumber;
    private String ifscCode;
    private String accountHolderName;

    @OneToOne
    @JoinColumn(name = "society_id")
    @JsonBackReference
    private Society society;

	public PaymentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentDetails(int paymentDetailsId, String upiId, String mobileNumber, String accountNumber,
			String ifscCode, String accountHolderName, Society society) {
		super();
		this.paymentDetailsId = paymentDetailsId;
		this.upiId = upiId;
		this.mobileNumber = mobileNumber;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
		this.accountHolderName = accountHolderName;
		this.society = society;
	}

	public int getPaymentDetailsId() {
		return paymentDetailsId;
	}

	public void setPaymentDetailsId(int paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

 
}