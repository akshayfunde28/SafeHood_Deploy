package com.SafeHood.DTO;

public class UserEmergencyResponse {

    private int user_Id;
    private String user_name;
    private String user_Phone;
    private String flat_No;
    private Boolean is_Emrgency;

    public UserEmergencyResponse() {
    }

    public UserEmergencyResponse(int user_Id, String user_name,
                                 String user_Phone, String flat_No,
                                 Boolean is_Emrgency) {
        this.user_Id = user_Id;
        this.user_name = user_name;
        this.user_Phone = user_Phone;
        this.flat_No = flat_No;
        this.is_Emrgency = is_Emrgency;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_Phone() {
        return user_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        this.user_Phone = user_Phone;
    }

    public String getFlat_No() {
        return flat_No;
    }

    public void setFlat_No(String flat_No) {
        this.flat_No = flat_No;
    }

    public Boolean getIs_Emrgency() {
        return is_Emrgency;
    }

    public void setIs_Emrgency(Boolean is_Emrgency) {
        this.is_Emrgency = is_Emrgency;
    }
}