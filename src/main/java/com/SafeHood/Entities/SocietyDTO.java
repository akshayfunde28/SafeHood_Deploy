package com.SafeHood.Entities;

public class SocietyDTO {

    private String username;
    private String societyAddress;
    private String adminPassword;
    private String pinCode;
    private String password;
    private String status;

    // Constructor
    public SocietyDTO(String username, String societyAddress,
                      String adminPassword, String pinCode,
                      String password, String status) {

        this.username = username;
        this.societyAddress = societyAddress;
        this.adminPassword = adminPassword;
        this.pinCode = pinCode;
        this.password = password;
        this.status = status;
    }

    // Getters
    public String getUsername() { return username; }
    public String getSocietyAddress() { return societyAddress; }
    public String getAdminPassword() { return adminPassword; }
    public String getPinCode() { return pinCode; }
    public String getPassword() { return password; }
    public String getStatus() { return status; }

    // Setters (optional)
    public void setUsername(String username) { this.username = username; }
    public void setSocietyAddress(String societyAddress) { this.societyAddress = societyAddress; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
    public void setPassword(String password) { this.password = password; }
    public void setStatus(String status) { this.status = status; }
}