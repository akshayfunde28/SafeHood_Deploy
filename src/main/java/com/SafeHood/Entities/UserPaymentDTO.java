package com.SafeHood.Entities;

public class UserPaymentDTO {

    private int userId;
    private String userName;
    private String flatNo;
    private double maintenanceAmount;
    private double fineAmount;
    private double totalAmount;
    private int paymentId;
    private String status;
    private String fineReason;

    // constructor
    public UserPaymentDTO(int paymentId, int userId, String userName, String flatNo,
            double maintenanceAmount, double fineAmount,
            double totalAmount, String status, String fineReason){

        this.userId = userId;
        this.paymentId = paymentId ;
        this.userName = userName;
        this.flatNo = flatNo;
        this.maintenanceAmount = maintenanceAmount;
        this.fineAmount = fineAmount;
        this.totalAmount = totalAmount;
        this.status = status;
        this.fineReason = fineReason;
    }

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFineReason() {
		return fineReason;
	}

	public void setFineReason(String fineReason) {
		this.fineReason = fineReason;
	}

  
}
