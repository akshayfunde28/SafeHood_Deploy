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
@Table(name="Guard")
public class Guard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int guard_Id ;
	private String guard_Name;
	private String guard_Phone;
	private String guard_Role;
	private String guard_Username;
	private String guard_Password;
	private String guard_Shift;
	private String guard_Joiningdate;
	@ManyToOne
	@JoinColumn(name="Society_Guard")
	@JsonBackReference
   private Society society ;
	public Guard() {
		super();
	}
	 
	public Guard(int guard_Id, String guard_Name, String guard_Phone, String guard_Role, String guard_Username,
			String guard_Password, String guard_Shift, String guard_Joiningdate, Society society) {
		super();
		this.guard_Id = guard_Id;
		this.guard_Name = guard_Name;
		this.guard_Phone = guard_Phone;
		this.guard_Role = guard_Role;
		this.guard_Username = guard_Username;
		this.guard_Password = guard_Password;
		this.guard_Shift = guard_Shift;
		this.guard_Joiningdate = guard_Joiningdate;
		this.society = society;
	}

	public String getGuard_Role() {
		return guard_Role;
	}

	public void setGuard_Role(String guard_Role) {
		this.guard_Role = guard_Role;
	}

	public String getGuard_Username() {
		return guard_Username;
	}

	public void setGuard_Username(String guard_Username) {
		this.guard_Username = guard_Username;
	}

	public String getGuard_Password() {
		return guard_Password;
	}

	public void setGuard_Password(String guard_Password) {
		this.guard_Password = guard_Password;
	}

	public int getGuard_Id() {
		return guard_Id;
	}
	public void setGuard_Id(int guard_Id) {
		this.guard_Id = guard_Id;
	}
	public String getGuard_Name() {
		return guard_Name;
	}
	public void setGuard_Name(String guard_Name) {
		this.guard_Name = guard_Name;
	}
	public String getGuard_Phone() {
		return guard_Phone;
	}
	public void setGuard_Phone(String guard_Phone) {
		this.guard_Phone = guard_Phone;
	}
	public String getGuard_Shift() {
		return guard_Shift;
	}
	public void setGuard_Shift(String guard_Shift) {
		this.guard_Shift = guard_Shift;
	}
	public String getGuard_Joiningdate() {
		return guard_Joiningdate;
	}
	public void setGuard_Joiningdate(String guard_Joiningdate) {
		this.guard_Joiningdate = guard_Joiningdate;
	}
	public Society getSociety() {
		return society;
	}
	public void setSociety(Society society) {
		this.society = society;
	}
	
	
	
	

}
