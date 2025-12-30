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
@Table(name = "EventHallBooking")
public class EventHallBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer hallID;     
    private Integer residentID; 
    private String name;    
    private String date;          
    private String timeSlot;      
    private String purpose;       
    private String status;      
    private String remark;       

    @ManyToOne
    @JoinColumn(name = "Society_EventBookings")
    @JsonBackReference
    private Society society;
 
 

	 

	public EventHallBooking(Integer hallID, Integer residentID, String name, String date, String timeSlot,
			String purpose, String status, String remark, Society society) {
		super();
		this.hallID = hallID;
		this.residentID = residentID;
		this.name = name;
		this.date = date;
		this.timeSlot = timeSlot;
		this.purpose = purpose;
		this.status = status;
		this.remark = remark;
		this.society = society;
	}

	public Integer getResidentID() {
		return residentID;
	}

	public void setResidentID(Integer residentID) {
		this.residentID = residentID;
	}

	public EventHallBooking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHallID() {
		return hallID;
	}

	public void setHallID(Integer hallID) {
		this.hallID = hallID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}

	
	
	
	
}
