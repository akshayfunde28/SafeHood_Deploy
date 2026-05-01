package com.SafeHood.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bugreport {
	@Id  
	@GeneratedValue(strategy  = GenerationType.AUTO)
	 private int id ;
	 private String name;
	 private String email;
	  private String title;
	  private String description;
	  private String stepsToReproduce;
	  private String severity;
	  private String category;
	  private String status ; 
	  public Bugreport() {
		super();
		// TODO Auto-generated constructor stub
	  }
	   
	  

	  public Bugreport(int id, String name, String email, String title, String description, String stepsToReproduce,
			String severity, String category, String status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.title = title;
		this.description = description;
		this.stepsToReproduce = stepsToReproduce;
		this.severity = severity;
		this.category = category;
		this.status = status;
	}
	  
	  
	  public int getId() {
		return id;
	}
	  public void setId(int id) {
		  this.id = id;
	  }
	  public String getStatus() {
		  return status;
	  }

	  public void setStatus(String status) {
		  this.status = status;
	  }
	  public String getName() {
		  return name;
	  }
	  public void setName(String name) {
		  this.name = name;
	  }
	  public String getEmail() {
		  return email;
	  }
	  public void setEmail(String email) {
		  this.email = email;
	  }
	  public String getTitle() {
		  return title;
	  }
	  public void setTitle(String title) {
		  this.title = title;
	  }
	  public String getDescription() {
		  return description;
	  }
	  public void setDescription(String description) {
		  this.description = description;
	  }
	  public String getStepsToReproduce() {
		  return stepsToReproduce;
	  }
	  public void setStepsToReproduce(String stepsToReproduce) {
		  this.stepsToReproduce = stepsToReproduce;
	  }
	  public String getSeverity() {
		  return severity;
	  }
	  public void setSeverity(String severity) {
		  this.severity = severity;
	  }
	  public String getCategory() {
		  return category;
	  }
	  public void setCategory(String category) {
		  this.category = category;
	  }
	  
}

