package com.SafeHood.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Emergency_Trigger_logs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer userId;


	private String role;

	private String locationLink;

	private LocalDateTime triggeredAt;

	@ManyToOne
	@JoinColumn(name = "society_Id")
	@JsonBackReference
	private Society society;

	public Emergency_Trigger_logs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Emergency_Trigger_logs(Integer id, Integer userId, String role, String locationLink,
			LocalDateTime triggeredAt, Society society) {
		super();
		this.id = id;
		this.userId = userId;
		this.role = role;
		this.locationLink = locationLink;
		this.triggeredAt = triggeredAt;
		this.society = society;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLocationLink() {
		return locationLink;
	}

	public void setLocationLink(String locationLink) {
		this.locationLink = locationLink;
	}

	public LocalDateTime getTriggeredAt() {
		return triggeredAt;
	}

	public void setTriggeredAt(LocalDateTime triggeredAt) {
		this.triggeredAt = triggeredAt;
	}

}
