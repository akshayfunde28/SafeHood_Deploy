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
@Table(name="Notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notice_Id ;
	private String notice_Title ;
	private String notice_Description;
	private String notice_Date;
	@ManyToOne
	@JoinColumn(name="Society_User")
	@JsonBackReference
	private Society society ;
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Notice(int notice_Id, String notice_Title, String notice_Description, String notice_Date, Society society) {
		super();
		this.notice_Id = notice_Id;
		this.notice_Title = notice_Title;
		this.notice_Description = notice_Description;
		this.notice_Date = notice_Date;
		this.society = society;
	}
	public int getNotice_Id() {
		return notice_Id;
	}
	public void setNotice_Id(int notice_Id) {
		this.notice_Id = notice_Id;
	}
	public String getNotice_Title() {
		return notice_Title;
	}
	public void setNotice_Title(String notice_Title) {
		this.notice_Title = notice_Title;
	}
	public String getNotice_Description() {
		return notice_Description;
	}
	public void setNotice_Description(String notice_Description) {
		this.notice_Description = notice_Description;
	}
	public String getNotice_Date() {
		return notice_Date;
	}
	public void setNotice_Date(String notice_Date) {
		this.notice_Date = notice_Date;
	}
	public Society getSociety() {
		return society;
	}
	public void setSociety(Society society) {
		this.society = society;
	}
	
	
}
