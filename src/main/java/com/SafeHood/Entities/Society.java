package com.SafeHood.Entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="SafeHood_Society")
public class Society {
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private	int society_Id ;
	@Column(unique = true, nullable = false)/// error occurred remove this line 
	private	String username;
	private	String societyAddress;
	private String adminPassword; 
	private	String pinCode ;
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")
	@JsonManagedReference
	private List<User> user = new ArrayList<>();                         					 	   // User mapping 

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")
	@JsonManagedReference
	private List<Notice> notice = new ArrayList<>();                         			   // Notice mapping 
	 
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")
	@JsonManagedReference
	private List<Complaint> complaint = new ArrayList<>();			    	// Complaint mapping 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")
	@JsonManagedReference
	private List<Events> event = new ArrayList<>();								// Event  mapping 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")
	@JsonManagedReference
	private List<SOS_Alert> sos = new ArrayList<>();		                    // Sos_Alert  mapping 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")
	@JsonManagedReference
	private List<Guard> guard = new ArrayList<>();						// Guard  mapping 

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")
	@JsonManagedReference
	private List<Guest> guest= new ArrayList<>();						// Guest  mapping 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society") // resident parking 
	@JsonManagedReference
	private List<Parking> parkingSlots = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society") // resident parking 
	@JsonManagedReference
	private List<GuestParking> guestParking = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "society")     // Event hall booking 
	@JsonManagedReference
	private List<EventHallBooking> eventHallBookings = new ArrayList<>();


 
 
	public Society(int society_Id, String username, String societyAddress, String adminPassword, String pinCode,
			String password, List<User> user, List<Notice> notice, List<Complaint> complaint, List<Events> event,
			List<SOS_Alert> sos, List<Guard> guard, List<Guest> guest, List<Parking> parkingSlots,
			List<GuestParking> guestParking, List<EventHallBooking> eventHallBookings) {
		super();
		this.society_Id = society_Id;
		this.username = username;
		this.societyAddress = societyAddress;
		this.adminPassword = adminPassword;
		this.pinCode = pinCode;
		this.password = password;
		this.user = user;
		this.notice = notice;
		this.complaint = complaint;
		this.event = event;
		this.sos = sos;
		this.guard = guard;
		this.guest = guest;
		this.parkingSlots = parkingSlots;
		this.guestParking = guestParking;
		this.eventHallBookings = eventHallBookings;
	}

	public Society() {
		super();
	 
	}
	

	public List<GuestParking> getGuestParking() {
		return guestParking;
	}

	public void setGuestParking(List<GuestParking> guestParking) {
		this.guestParking = guestParking;
	}

	public int getSociety_Id() {
		return society_Id;
	}

	public void setSociety_Id(int society_Id) {
		this.society_Id = society_Id;
	}

	public List<EventHallBooking> getEventHallBookings() {
		return eventHallBookings;
	}

	public void setEventHallBookings(List<EventHallBooking> eventHallBookings) {
		this.eventHallBookings = eventHallBookings;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSocietyAddress() {
		return societyAddress;
	}

	public void setSocietyAddress(String society_Address) {
		this.societyAddress = society_Address;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public List<Notice> getNotice() {
		return notice;
	}

	public void setNotice(List<Notice> notice) {
		this.notice = notice;
	}

	public List<Complaint> getComplaint() {
		return complaint;
	}

	public void setComplaint(List<Complaint> complaint) {
		this.complaint = complaint;
	}

	public List<Events> getEvent() {
		return event;
	}

	public void setEvent(List<Events> event) {
		this.event = event;
	}

	public List<SOS_Alert> getSos() {
		return sos;
	}

	public void setSos(List<SOS_Alert> sos) {
		this.sos = sos;
	}

	public List<Guard> getGuard() {
		return guard;
	}

	public void setGuard(List<Guard> guard) {
		this.guard = guard;
	}

	public List<Guest> getGuest() {
		return guest;
	}

	public void setGuest(List<Guest> guest) {
		this.guest = guest;
	}

	public List<Parking> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(List<Parking> parkingSlots) {
		this.parkingSlots = parkingSlots;
	}

	

}
