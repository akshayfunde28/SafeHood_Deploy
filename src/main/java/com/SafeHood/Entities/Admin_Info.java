package com.SafeHood.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Admin")
public class Admin_Info {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer adminID ; 
	@Column(name="Name")
	private  String adminName ; 
	@Column(name="Email")
	private String adminEmail ;
	@Column(name="Password")
	private String adminPassword ;
	@Column(name="Phone No.")
	private String phoneNo;
	
	public Admin_Info(Integer adminID, String adminName, String adminEmail, String adminPassword, String phoneNo) {
		super();
		this.adminID = adminID;
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
		this.phoneNo = phoneNo;
	}
	public Admin_Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getAdminID() {
		return adminID;
	}
	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	

}
