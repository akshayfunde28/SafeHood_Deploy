package com.SafeHood.Entities;

public class SocietyDTO {

    private String username;
    private String societyAddress;
    private String adminPassword;
    private String pinCode;
    private String password;

    public SocietyDTO(String username, String societyAddress, String adminPassword, String pinCode, String password) {
        this.username = username;
        this.societyAddress = societyAddress;
        this.adminPassword = adminPassword;
        this.pinCode = pinCode;
        this.password = password;
    }

  
    public String getUsername() { return username; }
    public String getSocietyAddress() { return societyAddress; }
    public String getAdminPassword() { return adminPassword; }
    public String getPinCode() { return pinCode; }
    public String getPassword() { return password; }
}