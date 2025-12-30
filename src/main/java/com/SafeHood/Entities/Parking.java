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
@Table(name = "Parking")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int slotId;

    private String slotNo;        
    private String occupiedBy;     
    private String flatNo;          
    private String residentName; 
    private String status;         
    @ManyToOne
    @JoinColumn(name = "Society_Parking")
    @JsonBackReference
    private Society society;        

    // ---------------- Constructors ----------------
    public Parking() {
        super();
    }

    public Parking(int slotId, String slotNo, String occupiedBy, String flatNo, 
                   String residentName, String status, Society society) {
        super();
        this.slotId = slotId;
        this.slotNo = slotNo;
        this.occupiedBy = occupiedBy;
        this.flatNo = flatNo;
        this.residentName = residentName;
        this.status = status;
        this.society = society;
    }

    // ---------------- Getters & Setters ----------------
    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public String getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(String occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }
}
