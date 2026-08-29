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
@Table(name = "Firebase_Token")
public class FirebaseTokenMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int token_Id;
	private Integer userId;
	private String fcmToken;
	private String role;
	private Boolean is_Emrgency = false;
	@ManyToOne
	@JoinColumn(name = "society_id")
	@JsonBackReference
	private Society society;

	public FirebaseTokenMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FirebaseTokenMapping(int token_Id, Integer userId, String fcmToken, String role, Boolean is_Emrgency,
			Society society) {
		super();
		this.token_Id = token_Id;
		this.userId = userId;
		this.fcmToken = fcmToken;
		this.role = role;
		this.is_Emrgency = is_Emrgency;
		this.society = society;
	}

	public Boolean getIs_Emrgency() {
		return is_Emrgency;
	}

	public void setIs_Emrgency(Boolean is_Emrgency) {
		this.is_Emrgency = is_Emrgency;
	}

	public int getToken_Id() {
		return token_Id;
	}

	public void setToken_Id(int token_Id) {
		this.token_Id = token_Id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fmcToken) {
		this.fcmToken = fmcToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

}
