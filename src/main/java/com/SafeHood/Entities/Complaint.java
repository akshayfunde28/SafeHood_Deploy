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
@Table(name="Complaint")
public class Complaint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int complaint_Id ;
	private String complaint_Title ;
	private String complaint_Description ;
	private String complaint_Status ;// pending /resolve 
	private String complaint_Date ;
	@ManyToOne
	@JoinColumn(name="Society_Complaints")
	@JsonBackReference
	private Society society ;
	
	public Complaint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Complaint(int complaint_Id, String complaint_Title, String complaint_Description, String complaint_Status,
			String complaint_Date, Society society) {
		super();
		this.complaint_Id = complaint_Id;
		this.complaint_Title = complaint_Title;
		this.complaint_Description = complaint_Description;
		this.complaint_Status = complaint_Status;
		this.complaint_Date = complaint_Date;
		this.society = society;
	}

	public int getComplaint_Id() {
		return complaint_Id;
	}

	public void setComplaint_Id(int complaint_Id) {
		this.complaint_Id = complaint_Id;
	}

	public String getComplaint_Title() {
		return complaint_Title;
	}

	public void setComplaint_Title(String complaint_Title) {
		this.complaint_Title = complaint_Title;
	}

	public String getComplaint_Description() {
		return complaint_Description;
	}

	public void setComplaint_Description(String complaint_Description) {
		this.complaint_Description = complaint_Description;
	}

	public String getComplaint_Status() {
		return complaint_Status;
	}

	public void setComplaint_Status(String complaint_Status) {
		this.complaint_Status = complaint_Status;
	}

	public String getComplaint_Date() {
		return complaint_Date;
	}

	public void setComplaint_Date(String complaint_Date) {
		this.complaint_Date = complaint_Date;
	}

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}


	
}
