package com.SafeHood.Entities;

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
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int guest_Id ;
	private String guest_Name ;
	private String guest_Phone ; 
	private String guest_Purpose ;
	private String guest_EntryTime ;
	private String guest_Address ; 
	private String guest_ResidentName;
	@ManyToOne
	@JoinColumn(name="Society_Guest")
	@JsonBackReference
	private Society society ;
	
	
	   public Guest(int guest_Id, String guest_Name, String guest_Phone, String guest_Purpose, String guest_EntryTime,
			String guest_Address, String guest_ResidentName, Society society) {
		super();
		this.guest_Id = guest_Id;
		this.guest_Name = guest_Name;
		this.guest_Phone = guest_Phone;
		this.guest_Purpose = guest_Purpose;
		this.guest_EntryTime = guest_EntryTime;
		this.guest_Address = guest_Address;
		this.guest_ResidentName = guest_ResidentName;
		this.society = society;
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

	public String getGuest_ResidentName() {
		return guest_ResidentName;
	}

	public void setGuest_ResidentName(String guest_ResidentName) {
		this.guest_ResidentName = guest_ResidentName;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

	

	
	
	
}
