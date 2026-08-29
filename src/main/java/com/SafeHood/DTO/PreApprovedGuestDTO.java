package com.SafeHood.DTO;

import java.time.LocalDateTime;

public class PreApprovedGuestDTO {

    private String guest_Name;
    private String guest_Phone;
    private String guest_Purpose;
    private Integer residentId;
    private String guest_Address;
    private String vehicleNumber;
    private LocalDateTime validTill;
    
	public PreApprovedGuestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PreApprovedGuestDTO(String guest_Name, String guest_Phone, String guest_Purpose, Integer residentId,
			String guest_Address, String vehicleNumber, LocalDateTime validTill) {
		super();
		this.guest_Name = guest_Name;
		this.guest_Phone = guest_Phone;
		this.guest_Purpose = guest_Purpose;
		this.residentId = residentId;
		this.guest_Address = guest_Address;
		this.vehicleNumber = vehicleNumber;
		this.validTill = validTill;
	}
	public String getGuest_Name() {
		return guest_Name;
	}
	public void setGuest_Name(String guest_Name) {
		this.guest_Name = guest_Name;
	}
	public String getGuest_Phone() {
		return guest_Phone;
	}
	public void setGuest_Phone(String guest_Phone) {
		this.guest_Phone = guest_Phone;
	}
	public String getGuest_Purpose() {
		return guest_Purpose;
	}
	public void setGuest_Purpose(String guest_Purpose) {
		this.guest_Purpose = guest_Purpose;
	}
	public Integer getResidentId() {
		return residentId;
	}
	public void setResidentId(Integer residentId) {
		this.residentId = residentId;
	}
	public String getGuest_Address() {
		return guest_Address;
	}
	public void setGuest_Address(String guest_Address) {
		this.guest_Address = guest_Address;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public LocalDateTime getValidTill() {
		return validTill;
	}
	public void setValidTill(LocalDateTime validTill) {
		this.validTill = validTill;
	}

    
}