package com.SafeHood.Services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SafeHood.Entities.Complaint;
import com.SafeHood.Entities.EventHallBooking;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.Guard;
import com.SafeHood.Entities.Guest;
import com.SafeHood.Entities.GuestParking;
import com.SafeHood.Entities.Notice;
import com.SafeHood.Entities.Parking;
import com.SafeHood.Entities.SOS_Alert;
import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.User;
import com.SafeHood.Repository.BookingHallRepo;
import com.SafeHood.Repository.ComplaintRepo;
import com.SafeHood.Repository.EventRepo;
import com.SafeHood.Repository.GuardRepo;
import com.SafeHood.Repository.GuestParkingRepo;
import com.SafeHood.Repository.GuestRepo;
import com.SafeHood.Repository.NoticeRepo;
import com.SafeHood.Repository.ParkingRepo;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Repository.SosRepo;
import com.SafeHood.Repository.UserRepo;

@Service
public class SafeHoodServices {
	
	@Autowired
	private ComplaintRepo complaintRepo;
	@Autowired
	private EventRepo eventRepo;
	@Autowired
	private GuardRepo guardRepo;
	@Autowired
	private GuestRepo guestRepo;
	@Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private SocietyRepo societyRepo ;
	@Autowired
	private SosRepo sosRepo ;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ParkingRepo parkingRepo;
	@Autowired
	private BookingHallRepo bookingHallRepo;
	@Autowired
	private GuestParkingRepo guestParkingRepo;


	  //--------------------->>>>>>>>>>>>>>>.Save All Data............<<<<<<<<<<<<<<<------------------------------
	
	// Society  #SAVE and UPDATE
	public void saveSociety(Society society) {
		this.societyRepo.save(society);
	}
	// Complaints	 #SAVE 
	public void 	saveComplaints(Society society,Complaint complaint) {
		complaint.setSociety(society);
		society.getComplaint().add(complaint);
//		societyRepo.save(society); // only if society is not exist already in that case we save society 
		this.complaintRepo.save(complaint);
	}
	//Events #SAVE and UPDATE
	public void saveEvents(Society society,Events event) {
		event.setSociety(society);
		society.getEvent().add(event);
		this.eventRepo.save(event);
	}
	//Guard #SAVE and UPDATE
	public void saveGuard(Society society,Guard guard) {
		guard.setSociety(society);
		society.getGuard().add(guard);
		this.guardRepo.save(guard);
	}
	//Guest #SAVE and UPDATE
	public void saveGuest(Society society,Guest guest) {
		guest.setSociety(society);
		society.getGuest().add(guest);
		this.guestRepo.save(guest);
	}
	//Notice  #SAVE and UPDATE
	public void saveNotice (Society society,Notice notice) {
		notice.setSociety(society);
		society.getNotice().add(notice);
		this.noticeRepo.save(notice);
	}
	//SOS  #SAVE and UPDATE
	public void saveSos(Society society,SOS_Alert sos_Alert) {
		sos_Alert.setSociety(society);
		society.getSos().add(sos_Alert);
		this.sosRepo.save(sos_Alert);
	}
	// User  #SAVE and UPDATE
	public void saveUser(Society society,User user) {
		user.setSociety(society);
		society.getUser().add(user);
		this.userRepo.save(user);
	}
	// Parking #SAVE and UPDATE
	public void saveParking(Society society, Parking parking) {
	    parking.setSociety(society);              // Link parking to the society
	    society.getParkingSlots().add(parking);   // Add parking to society’s parking list
	    this.parkingRepo.save(parking);           // Save parking to the DB
	}
	public void saveGuestParking(Society society, GuestParking guestParking) {
	    guestParking.setSociety(society);                   // Link guest parking entry to the society
	    society.getGuestParking().add(guestParking);       // Add guest parking record to society’s guest parking list
	    this.guestParkingRepo.save(guestParking);           // Save guest parking record to the database
	}

	 // event hall booking 
	public void saveHallBooking(Society society, EventHallBooking bookingHall) {
	    bookingHall.setSociety(society);
	    society.getEventHallBookings().add(bookingHall);
	    this.bookingHallRepo.save(bookingHall);
	}


	public boolean updateParking(Society society, int slotId, Parking updatedParking) {
	    Parking parking = parkingRepo.findById(slotId).orElse(null);

	    if (parking == null) {
	        return false; // not found
	    }
 	    parking.setResidentName(updatedParking.getResidentName());
	    parking.setFlatNo(updatedParking.getFlatNo());
	    parking.setOccupiedBy(updatedParking.getOccupiedBy());
	    parking.setStatus(updatedParking.getStatus());
	    parking.setSociety(society);

	    // Save back
	    parkingRepo.save(parking);
	    return true;
	}
	
	public boolean updateGuestParking(Society society, int slotId, GuestParking updatedGuestParking) {
	    GuestParking guestParking = guestParkingRepo.findById(slotId).orElse(null);

	    if (guestParking == null) {
	        return false; // Guest parking record not found
	    }

	    // Update all editable fields
	    guestParking.setGuestName(updatedGuestParking.getGuestName());
	    guestParking.setGuestVehicleNo(updatedGuestParking.getGuestVehicleNo());
	    guestParking.setGuestContact(updatedGuestParking.getGuestContact());
	    guestParking.setVisitingFlatNo(updatedGuestParking.getVisitingFlatNo());
	    guestParking.setResidentName(updatedGuestParking.getResidentName());
	    guestParking.setCheckInTime(updatedGuestParking.getCheckInTime());
	    guestParking.setCheckInDate(updatedGuestParking.getCheckInDate());
	    guestParking.setCheckOutDate(updatedGuestParking.getCheckOutDate());
	    guestParking.setCheckOutTime(updatedGuestParking.getCheckOutTime());
	    guestParking.setStatus(updatedGuestParking.getStatus());
	    guestParking.setApprovedByGuard(updatedGuestParking.getApprovedByGuard());
	    guestParking.setVehicleType(updatedGuestParking.getVehicleType());

	    // Save updated record
	    guestParkingRepo.save(guestParking);
	    return true;
	}

	
	public boolean updateHallBooking(Society society, int hallID, EventHallBooking updatedBooking) {
	    EventHallBooking bookingHall = bookingHallRepo.findById(hallID).orElse(null);

	    if (bookingHall == null) {
	        return false; // Booking not found
	    }

	    // Update fields if provided
	    if (updatedBooking.getDate() != null) {
	        bookingHall.setDate(updatedBooking.getDate());
	    }
	    if (updatedBooking.getTimeSlot() != null) {
	        bookingHall.setTimeSlot(updatedBooking.getTimeSlot());
	    }
	    if (updatedBooking.getPurpose() != null) {
	        bookingHall.setPurpose(updatedBooking.getPurpose());
	    }
	    if (updatedBooking.getStatus() != null) {
	        bookingHall.setStatus(updatedBooking.getStatus());
	    }
	    if (updatedBooking.getRemark() != null) {
	        bookingHall.setRemark(updatedBooking.getRemark());
	    }

	    bookingHall.setSociety(society);

	    bookingHallRepo.save(bookingHall);
	    return true;
	}

	 
	
	
	
	
	
	
	
	
	
	
	
	

}
