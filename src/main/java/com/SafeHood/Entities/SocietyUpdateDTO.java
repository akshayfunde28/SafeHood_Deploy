package com.SafeHood.Entities;


public class SocietyUpdateDTO {

    private String username;
    private String societyAddress;
    private String adminPassword;
    private String pinCode;
    private String password;
	public SocietyUpdateDTO(String username, String societyAddress, String adminPassword, String pinCode,
			String password) {
		super();
		this.username = username;
		this.societyAddress = societyAddress;
		this.adminPassword = adminPassword;
		this.pinCode = pinCode;
		this.password = password;
	}
	public SocietyUpdateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSocietyAddress() {
		return societyAddress;
	}
	public void setSocietyAddress(String societyAddress) {
		this.societyAddress = societyAddress;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    
    // Getters & Setters
}