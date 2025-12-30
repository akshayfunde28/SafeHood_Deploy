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
@Table(name = "GuestParking")
public class GuestParking {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int slotId;              // Unique ID for the parking slot
	    private String slotNo;           // Slot number or name (e.g., G1, G2)
	    private String guestName;        // Name of the guest
	    private String guestVehicleNo;   // Vehicle number of the guest
	    private String guestContact;     // Contact number of the guest
	    private String visitingFlatNo;   // Flat number the guest is visiting
	    private String residentName;     // Name of the resident being visited
	    private String checkInTime;      // Time when guest entered
	    private String checkInDate;    // date when guest enter 
	    private String checkOutDate; 
	    private String checkOutTime;     // Time when guest left
	    private String status;           // e.g., "Occupied", "Available", "Reserved"
	    private String approvedByGuard;  // Guard name or ID who approved entry
	    private String vehicleType;        
	    @ManyToOne 
	    @JoinColumn(name = "Society_Parking") 
	    @JsonBackReference
	    private Society society; 
		public GuestParking() {
			super();
			// TODO Auto-generated constructor stub
		}
		public GuestParking(int slotId, String slotNo, String guestName, String guestVehicleNo, String guestContact,
				String visitingFlatNo, String residentName, String checkInTime, String checkInDate, String checkOutDate,
				String checkOutTime, String status, String approvedByGuard, String vehicleType, Society society) {
			super();
			this.slotId = slotId;
			this.slotNo = slotNo;
			this.guestName = guestName;
			this.guestVehicleNo = guestVehicleNo;
			this.guestContact = guestContact;
			this.visitingFlatNo = visitingFlatNo;
			this.residentName = residentName;
			this.checkInTime = checkInTime;
			this.checkInDate = checkInDate;
			this.checkOutDate = checkOutDate;
			this.checkOutTime = checkOutTime;
			this.status = status;
			this.approvedByGuard = approvedByGuard;
			this.vehicleType = vehicleType;
			this.society = society;
		}
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
		public String getGuestName() {
			return guestName;
		}
		public void setGuestName(String guestName) {
			this.guestName = guestName;
		}
		public String getGuestVehicleNo() {
			return guestVehicleNo;
		}
		public void setGuestVehicleNo(String guestVehicleNo) {
			this.guestVehicleNo = guestVehicleNo;
		}
		public String getGuestContact() {
			return guestContact;
		}
		public void setGuestContact(String guestContact) {
			this.guestContact = guestContact;
		}
		public String getVisitingFlatNo() {
			return visitingFlatNo;
		}
		public void setVisitingFlatNo(String visitingFlatNo) {
			this.visitingFlatNo = visitingFlatNo;
		}
		public String getResidentName() {
			return residentName;
		}
		public void setResidentName(String residentName) {
			this.residentName = residentName;
		}
		public String getCheckInTime() {
			return checkInTime;
		}
		public void setCheckInTime(String checkInTime) {
			this.checkInTime = checkInTime;
		}
		public String getCheckInDate() {
			return checkInDate;
		}
		public void setCheckInDate(String checkInDate) {
			this.checkInDate = checkInDate;
		}
		public String getCheckOutDate() {
			return checkOutDate;
		}
		public void setCheckOutDate(String checkOutDate) {
			this.checkOutDate = checkOutDate;
		}
		public String getCheckOutTime() {
			return checkOutTime;
		}
		public void setCheckOutTime(String checkOutTime) {
			this.checkOutTime = checkOutTime;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getApprovedByGuard() {
			return approvedByGuard;
		}
		public void setApprovedByGuard(String approvedByGuard) {
			this.approvedByGuard = approvedByGuard;
		}
		public String getVehicleType() {
			return vehicleType;
		}
		public void setVehicleType(String vehicleType) {
			this.vehicleType = vehicleType;
		}
		public Society getSociety() {
			return society;
		}
		public void setSociety(Society society) {
			this.society = society;
		}    
	    
	    
	    

}
