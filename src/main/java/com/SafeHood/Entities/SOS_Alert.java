package com.SafeHood.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="SOS")
public class SOS_Alert {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sos_Id ;
	private String phone_no ;
	@ManyToOne
	@JoinColumn(name="Society_User")
	@JsonBackReference
	private Society society ;
	public SOS_Alert() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SOS_Alert(int sos_Id,String phone_no, Society society) {
		super();
		this.sos_Id = sos_Id;
		this.phone_no = phone_no;
		this.society = society;
	}
	public int getSos_Id() {
		return sos_Id;
	}
	public void setSos_Id(int sos_Id) {
		this.sos_Id = sos_Id;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public Society getSociety() {
		return society;
	}
	public void setSociety(Society society) {
		this.society = society;
	}
	
	
}
