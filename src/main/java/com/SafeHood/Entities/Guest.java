package com.SafeHood.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.Table;

@Entity
@Table(name="Guest")
public class Guest {
	// add this in side the resident table one to many relationship 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int guest_Id ;
	private String guest_Name ;
	private String guest_Phone ; 
	private String guest_Purpose ;
	private Integer residentId;
	private String guest_EntryTime ;
	private String verificationStatus; // pending approved reject like this 
	private String guest_Address ; 
	private Boolean preApproved;
    private String verificationCode;
    private String entryStatus; // entered, exited, expired, not entered
    // Validity
    private LocalDateTime createdAt;
    private String vehicleNumber;
    private LocalDateTime validTill;
	@ManyToOne
	@JoinColumn(name="Society_Guest")
	@JsonBackReference
	private Society society ;
	
	
	public Guest(int guest_Id, String guest_Name, String guest_Phone, String guest_Purpose, Integer residentId,
			String guest_EntryTime, String verificationStatus, String guest_Address, Boolean preApproved,
			String verificationCode, String entryStatus, LocalDateTime createdAt, String vehicleNumber,
			LocalDateTime validTill, Society society) {
		super();
		this.guest_Id = guest_Id;
		this.guest_Name = guest_Name;
		this.guest_Phone = guest_Phone;
		this.guest_Purpose = guest_Purpose;
		this.residentId = residentId;
		this.guest_EntryTime = guest_EntryTime;
		this.verificationStatus = verificationStatus;
		this.guest_Address = guest_Address;
		this.preApproved = preApproved;
		this.verificationCode = verificationCode;
		this.entryStatus = entryStatus;
		this.createdAt = createdAt;
		this.vehicleNumber = vehicleNumber;
		this.validTill = validTill;
		this.society = society;
	}

	
	
	public Integer getResidentId() {
		return residentId;
	}



	public void setResidentId(Integer residentId) {
		this.residentId = residentId;
	}



	public String getVerificationStatus() {
		return verificationStatus;
	}



	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}



	public Boolean getPreApproved() {
		return preApproved;
	}



	public void setPreApproved(Boolean preApproved) {
		this.preApproved = preApproved;
	}



	public String getVerificationCode() {
		return verificationCode;
	}



	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}



	public String getEntryStatus() {
		return entryStatus;
	}



	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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



	public Guest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getGuest_Id() {
		return guest_Id;
	}

	public void setGuest_Id(int guest_Id) {
		this.guest_Id = guest_Id;
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

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

	

	
	
	
}
