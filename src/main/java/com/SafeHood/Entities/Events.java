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
@Table(name="Events")
public class Events {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int event_Id ;
	private String event_Title ;
	private String event_Description ;
	private String event_Date ;
	private String event_Location ;
	@ManyToOne
	@JoinColumn(name="Society_Events")
	@JsonBackReference
	private Society society ;
	
	public Events() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Events(int event_Id, String event_Title, String event_Description, String event_Date, String event_Location,
			Society society) {
		super();
		this.event_Id = event_Id;
		this.event_Title = event_Title;
		this.event_Description = event_Description;
		this.event_Date = event_Date;
		this.event_Location = event_Location;
		this.society = society;
	}
	public int getEvent_Id() {
		return event_Id;
	}
	public void setEvent_Id(int event_Id) {
		this.event_Id = event_Id;
	}
	public String getEvent_Title() {
		return event_Title;
	}
	public void setEvent_Title(String event_Title) {
		this.event_Title = event_Title;
	}
	public String getEvent_Description() {
		return event_Description;
	}
	public void setEvent_Description(String event_Description) {
		this.event_Description = event_Description;
	}
	public String getEvent_Date() {
		return event_Date;
	}
	public void setEvent_Date(String event_Date) {
		this.event_Date = event_Date;
	}
	public String getEvent_Location() {
		return event_Location;
	}
	public void setEvent_Location(String event_Location) {
		this.event_Location = event_Location;
	}
	public Society getSociety() {
		return society;
	}
	public void setSociety(Society society) {
		this.society = society;
	}

	
	
}
