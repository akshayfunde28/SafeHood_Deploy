package com.SafeHood.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="User_")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_Id")
	private int user_Id ;
	private String user_name ;
	private String user_password ;
	private String user_Email ;
	private String user_Phone ;
	private String role;
	private String flat_No ;
	@ManyToOne
	@JoinColumn(name="Society_User")
	@JsonBackReference
	private Society society ;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int user_Id, String user_name, String user_password, String user_Email, String user_Phone, String role,
			String flat_No, Society society) {
		super();
		this.user_Id = user_Id;
		this.user_name = user_name;
		this.user_password = user_password;
		this.user_Email = user_Email;
		this.user_Phone = user_Phone;
		this.role = role;
		this.flat_No = flat_No;
		this.society = society;
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
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_Email() {
		return user_Email;
	}
	public void setUser_Email(String user_Email) {
		this.user_Email = user_Email;
	}
	public String getUser_Phone() {
		return user_Phone;
	}
	public void setUser_Phone(String user_Phone) {
		this.user_Phone = user_Phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFlat_No() {
		return flat_No;
	}
	public void setFlat_No(String flat_No) {
		this.flat_No = flat_No;
	}
	public Society getSociety() {
		return society;
	}
	public void setSociety(Society society) {
		this.society = society;
	}
	
	
	
}
