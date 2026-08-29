package com.SafeHood.DTO;

public class GuestNotificationDTO {

    private String guest_Name;
    private String guest_Phone;
    private String guest_Purpose;
    private String guest_EntryTime;
    private String guest_Address;
    private String vehicle_Information; // optional
    
    
    
	public GuestNotificationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GuestNotificationDTO(String guest_Name, String guest_Phone, String guest_Purpose, String guest_EntryTime,
			String guest_Address, String vehicle_Information) {
		super();
		this.guest_Name = guest_Name;
		this.guest_Phone = guest_Phone;
		this.guest_Purpose = guest_Purpose;
		this.guest_EntryTime = guest_EntryTime;
		this.guest_Address = guest_Address;
		this.vehicle_Information = vehicle_Information;
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
	public String getGuest_EntryTime() {
		return guest_EntryTime;
	}
	public void setGuest_EntryTime(String guest_EntryTime) {
		this.guest_EntryTime = guest_EntryTime;
	}
	public String getGuest_Address() {
		return guest_Address;
	}
	public void setGuest_Address(String guest_Address) {
		this.guest_Address = guest_Address;
	}
	public String getVehicle_Information() {
		return vehicle_Information;
	}
	public void setVehicle_Information(String vehicle_Information) {
		this.vehicle_Information = vehicle_Information;
	}

    // Getters and Setters
    
}