package com.SafeHood.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SafeHood.DTO.GuestNotificationDTO;
import com.SafeHood.DTO.GuestRequestDTO;
import com.SafeHood.DTO.PreApprovedGuestDTO;
import com.SafeHood.DTO.UserEmergencyResponse;
import com.SafeHood.Entities.Complaint;
import com.SafeHood.Entities.Emergency_Trigger_logs;
import com.SafeHood.Entities.EventHallBooking;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.FirebaseTokenMapping;
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
import com.SafeHood.Repository.EmergencyTriggerLogsRepository;
import com.SafeHood.Repository.EventRepo;
import com.SafeHood.Repository.FirebaseTokenMappingRepo;
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
	private SocietyRepo societyRepo;
	@Autowired
	private SosRepo sosRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ParkingRepo parkingRepo;
	@Autowired
	private BookingHallRepo bookingHallRepo;
	@Autowired
	private GuestParkingRepo guestParkingRepo;
	@Autowired
	private FirebaseTokenMappingRepo tokenRepo;
	@Autowired
	private BookingHallRepo hallBookingRepo;
	@Autowired
	private EmergencyTriggerLogsRepository emergencyTriggerLogsRepository;
   @Autowired
   private NotificationService notificationService ;
	// --------------------->>>>>>>>>>>>>>>.Save All
	// Data............<<<<<<<<<<<<<<<------------------------------

	// Society #SAVE and UPDATE
	public void saveSociety(Society society) {
		this.societyRepo.save(society);
	}

	// Complaints #SAVE
	public void saveComplaints(Society society, Complaint complaint) {
		complaint.setSociety(society);
		society.getComplaint().add(complaint);
//		societyRepo.save(society); // only if society is not exist already in that case we save society 
		this.complaintRepo.save(complaint);
	}

	// Events #SAVE and UPDATE
	public void saveEvents(Society society, Events event) {
		event.setSociety(society);
		society.getEvent().add(event);
		this.eventRepo.save(event);
	}

	// Guard #SAVE and UPDATE
	public void saveGuard(Society society, Guard guard) {
		guard.setSociety(society);
		guardRepo.save(guard);

		FirebaseTokenMapping tokenMapping = new FirebaseTokenMapping();
		tokenMapping.setUserId(guard.getGuard_Id());
		tokenMapping.setFcmToken(null);
		tokenMapping.setRole("GUARD");
		tokenMapping.setSociety(society);

		tokenRepo.save(tokenMapping);
	}

	// save Guest by guard 
	public String requestGuestEntry(
	        GuestRequestDTO guestDTO) {

	    // Fetch Resident
	    User resident = userRepo 
	            .findById(guestDTO.getResidentId())
	            .orElseThrow(() ->
	                    new RuntimeException("Resident not found"));

	    // Fetch Society
	    Society society = resident.getSociety();

	    Guest guest = new Guest();

	    // Guest Details
	    guest.setGuest_Name(guestDTO.getGuest_Name());
	    guest.setGuest_Phone(guestDTO.getGuest_Phone());
	    guest.setGuest_Purpose(guestDTO.getGuest_Purpose());
	    guest.setGuest_Address(guestDTO.getGuest_Address());
	    guest.setVehicleNumber(guestDTO.getVehicleNumber());

	    // Resident Details
	    guest.setResidentId(guestDTO.getResidentId());

	    // Default Values
	    guest.setVerificationStatus("PENDING");

	    guest.setPreApproved(false);

	    guest.setEntryStatus("NOT_ENTERED");

	    guest.setCreatedAt(LocalDateTime.now());

	    // Society Mapping
	    guest.setSociety(society);

	    // Save Guest
	    guestRepo.save(guest);
	 // Create notification DTO
	    GuestNotificationDTO notificationDTO =
	            new GuestNotificationDTO();

	    notificationDTO.setGuest_Name(
	            guestDTO.getGuest_Name());

	    notificationDTO.setGuest_Phone(
	            guestDTO.getGuest_Phone());

	    notificationDTO.setGuest_Purpose(
	            guestDTO.getGuest_Purpose());

	    notificationDTO.setGuest_Address(
	            guestDTO.getGuest_Address());

	    notificationDTO.setVehicle_Information(
	            guestDTO.getVehicleNumber());

	    notificationDTO.setGuest_EntryTime(
	            LocalDateTime.now().toString());

	    // Send Notification
	    notificationService.sendGuestNotification(
	            society.getUsername(),
	            guestDTO.getResidentId(),
	            notificationDTO
	    );

	    return "Guest Request Sent To Resident";
	}
	
	// pre-approved guest by resident 
	public String preApproveGuest(
	        PreApprovedGuestDTO guestDTO) {

	    // Fetch Resident
	    User resident = userRepo 
	            .findById(guestDTO.getResidentId())
	            .orElseThrow(() ->
	                    new RuntimeException("Resident not found"));

	    // Fetch Society
	    Society society = resident.getSociety();

	    Guest guest = new Guest();

	    // Guest Details
	    guest.setGuest_Name(guestDTO.getGuest_Name());

	    guest.setGuest_Phone(
	            guestDTO.getGuest_Phone());

	    guest.setGuest_Purpose(
	            guestDTO.getGuest_Purpose());

	    guest.setGuest_Address(
	            guestDTO.getGuest_Address());

	    guest.setVehicleNumber(
	            guestDTO.getVehicleNumber());

	    // Resident
	    guest.setResidentId(
	            guestDTO.getResidentId());

	    // Pre Approval
	    guest.setPreApproved(true);

	    guest.setVerificationStatus(
	            "PRE_APPROVED");

	    // Entry Status
	    guest.setEntryStatus(
	            "NOT_ENTERED");

	    // Generate 6 Digit Code
	    String code = String.valueOf(
	            (int)((Math.random() * 900000) + 100000)
	    );

	    guest.setVerificationCode(code);

	    // Time
	    guest.setCreatedAt(LocalDateTime.now());

	    guest.setValidTill(
	            guestDTO.getValidTill());

	    // Society Mapping
	    guest.setSociety(society);

	    // Save
	    guestRepo.save(guest);

	    return "verification code : " + code;
	}

	// get all guest list of particular resident 
	public List<Guest> getResidentGuests(
	        Integer residentId) {

	    return guestRepo 
	            .findByResidentIdOrderByCreatedAtDesc(
	                    residentId);
	}
	
	// guest update by resident approve and reject case 
	public String updateGuestStatus(
	        Integer guestId,
	        String status) {

	    Guest guest = guestRepo
	            .findById(guestId)
	            .orElseThrow(() ->
	                    new RuntimeException("Guest not found"));

	    // APPROVED
	    if (status.equalsIgnoreCase("APPROVED")) {

	        guest.setVerificationStatus("APPROVED");

	        guest.setGuest_EntryTime(
	                LocalDateTime.now().toString());

	        guest.setEntryStatus("ENTERED");
	    }

	    // REJECTED
	    else if (status.equalsIgnoreCase("REJECTED")) {

	        guest.setVerificationStatus("REJECTED");
	        guest.setEntryStatus("NOT_ENTERED");
	        guest.setGuest_EntryTime("NULL");
	    }

	    else {

	        throw new RuntimeException(
	                "Invalid Status");
	    }

	    guestRepo.save(guest);

	    return "Guest Status Updated Successfully";
	}
	
	// get pre-approved guest list of society for guard 
	public List<Guest> getPreApprovedGuests(
	        String username) {

	    Society society = societyRepo
	            .findByUsername(username)
	            .orElseThrow(() ->
	                    new RuntimeException("Society not found"));

	    return guestRepo 
	            .findBySocietyAndVerificationStatusAndEntryStatusAndValidTillAfterOrderByCreatedAtDesc(
	                    society,
	                    "PRE_APPROVED",
	                    "NOT_ENTERED",
	                    LocalDateTime.now()
	            ); 
	}
	
	// get all PRE_APPROVED entered guests
	public List<Guest> getPreApprovedEnteredGuests(
	        String username) {

	    Society society = societyRepo
	            .findByUsername(username)
	            .orElseThrow(() -> 
	                    new RuntimeException("Society not found"));

	    return guestRepo
	            .findBySocietyAndVerificationStatusAndEntryStatusAndValidTillAfterOrderByCreatedAtDesc(
	                    society,
	                    "PRE_APPROVED",
	                    "ENTERED",
	                    LocalDateTime.now()
	            );
	}
	
	// update the status of pre approved guest by guard 
public String verifyPreApprovedGuest(
        Integer guestId,
        String verificationCode) {

    Guest guest = guestRepo
            .findById(guestId)
            .orElseThrow(() ->
                    new RuntimeException("Guest not found"));

    // Check Pre Approved
    if (!"PRE_APPROVED".equalsIgnoreCase(
            guest.getVerificationStatus())) {

        throw new RuntimeException(
                "Guest is not pre-approved");
    }

    // Check Already Entered
    if ("ENTERED".equalsIgnoreCase(
            guest.getEntryStatus())) {

        throw new RuntimeException(
                "Guest already entered");
    }

    // Check Expiry
    if (guest.getValidTill()
            .isBefore(LocalDateTime.now())) {

        guest.setEntryStatus("EXPIRED");

        guestRepo.save(guest);

        throw new RuntimeException(
                "Guest pass expired");
    }

    // Verify Code
    if (!guest.getVerificationCode()
            .equals(verificationCode)) {

        throw new RuntimeException(
                "Invalid verification code");
    }

    // Allow Entry
    guest.setEntryStatus("ENTERED");

    guest.setGuest_EntryTime(
            LocalDateTime.now().toString());

    guestRepo.save(guest);

    return "Guest verified successfully. Entry allowed.";
}
	
	// get all approved guest list of society 
	public List<Guest> getApprovedGuestList(
	        String username) {

	    Society society = societyRepo  
	            .findByUsername(username)
	            .orElseThrow(() ->
	                    new RuntimeException("Society not found"));

	    return  guestRepo.getApprovedGuestList(
	            society,
	            "APPROVED"); 
	}
	
	// get all guest of society for manager to track the guest 
	public List<Guest> getAllSocietyGuests(
	        String username) {

	    Society society = societyRepo
	            .findByUsername(username)
	            .orElseThrow(() ->
	                    new RuntimeException("Society not found"));

	    return guestRepo
	            .findBySocietyOrderByCreatedAtDesc(
	                    society);
	}
	
	
	// Get user data with its emergency status like it is in sos emergency list or not
	public List<UserEmergencyResponse> getUsersWithEmergency(String username) {

		Society society = societyRepo.getSocietyBySocietyName(username);

		List<User> users = society.getUser();

		List<UserEmergencyResponse> responseList = new ArrayList<>();

		for (User user : users) {

			Boolean emergencyStatus = false;

			Optional<FirebaseTokenMapping> tokenOptional = tokenRepo.findByUserId(user.getUser_Id());

			if (tokenOptional.isPresent()) {

				emergencyStatus = tokenOptional.get().getIs_Emrgency();
			}

			UserEmergencyResponse response = new UserEmergencyResponse(user.getUser_Id(), user.getUser_name(),
					user.getUser_Phone(), user.getFlat_No(), emergencyStatus);

			responseList.add(response);
		}

		return responseList;
	}

	// Notice #SAVE and UPDATE
	public void saveNotice(Society society, Notice notice) {
		notice.setSociety(society);
		society.getNotice().add(notice);
		this.noticeRepo.save(notice);
	}

	// SOS #SAVE and UPDATE
	public void saveSos(Society society, SOS_Alert sos_Alert) {
		sos_Alert.setSociety(society);
		society.getSos().add(sos_Alert);
		this.sosRepo.save(sos_Alert);
	}

	// User #SAVE and UPDATE
	public void saveUser(Society society, User user) {

		user.setSociety(society);
		society.getUser().add(user);

		this.userRepo.save(user);

		// 🔥 Add FirebaseTokenMapping (RESIDENT)
		FirebaseTokenMapping tokenMapping = new FirebaseTokenMapping();
		tokenMapping.setUserId(user.getUser_Id()); // resident primary key
		tokenMapping.setFcmToken(null);
		tokenMapping.setRole("RESIDENT");
		tokenMapping.setSociety(society);

		tokenRepo.save(tokenMapping);
	}

	// Parking #SAVE and UPDATE
	public void saveParking(Society society, Parking parking) {
		parking.setSociety(society); // Link parking to the society
		society.getParkingSlots().add(parking); // Add parking to society’s parking list
		this.parkingRepo.save(parking); // Save parking to the DB
	}

	public void saveGuestParking(Society society, GuestParking guestParking) {
		guestParking.setSociety(society); // Link guest parking entry to the society
		society.getGuestParking().add(guestParking); // Add guest parking record to society’s guest parking list
		this.guestParkingRepo.save(guestParking); // Save guest parking record to the database
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

	public EventHallBooking getHallBookingById(int hallID) {
		return hallBookingRepo.findById(hallID)
				.orElseThrow(() -> new RuntimeException("❌ Booking not found with ID: " + hallID));
	}

	
	// save emergency trigger logs 
	public String emergencyTriggerLog(String username, Integer userId, String role, String locationLink) {

		Society society = null;

		// RESIDENT
		if (role.equalsIgnoreCase("RESIDENT")) {

			User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

			society = user.getSociety();
		}

		// GUARD
		else if (role.equalsIgnoreCase("GUARD")) {

			Guard guard = guardRepo.findById(userId).orElseThrow(() -> new RuntimeException("Guard not found"));

			society = guard.getSociety();
		}

		// MANAGER
		else if (role.equalsIgnoreCase("MANAGER")) {

			society = societyRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Society not found"));
		}

		Emergency_Trigger_logs log = new Emergency_Trigger_logs();

		log.setRole(role);
		log.setLocationLink(locationLink);
		log.setTriggeredAt(LocalDateTime.now());

		// manager -> store society id
		if (role.equalsIgnoreCase("MANAGER")) {
			log.setUserId(society.getSociety_Id());
		} else {
			log.setUserId(userId); 
		}
 
		log.setSociety(society);

		emergencyTriggerLogsRepository.save(log);

		return "Emergency Alert Sent Successfully";
	}
	
	// get emergency trigger logs 
	public List<Emergency_Trigger_logs> getLast10DaysEmergencyLogs(
	        String username) {

	    Society society = societyRepo.findByUsername(username)
	            .orElseThrow(() ->
	                    new RuntimeException("Society not found"));

	    LocalDateTime last10Days =
	            LocalDateTime.now().minusDays(10);

	    return emergencyTriggerLogsRepository
	            .findBySocietyAndTriggeredAtAfterOrderByTriggeredAtDesc(
	                    society,
	                    last10Days
	            );
	}
	
	
	
	

}
