
package com.SafeHood.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.SafeHood.Entities.Complaint;
import com.SafeHood.Entities.EventHallBooking;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.Guard;
import com.SafeHood.Entities.Notice;
import com.SafeHood.Entities.Parking;
import com.SafeHood.Entities.PaymentRecord;
import com.SafeHood.Entities.PaymentRecordDTO;
import com.SafeHood.Entities.Society; 
import com.SafeHood.Entities.User;
import com.SafeHood.Repository.PaymentRecordRepo;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Repository.SosRepo;
import com.SafeHood.Repository.UserRepo;
import com.SafeHood.Services.SafeHoodServices;

@RestController
@RequestMapping("/safeHood")
public class User_Controller {
    
    @Autowired
    SafeHoodServices safeHoodServices;
    @Autowired
    SocietyRepo societyRepo;
    @Autowired
    PaymentRecordRepo paymentRecordRepo ;
	 
	@Autowired
	private UserRepo userRepo;
 // Society Login  
    @GetMapping("/{username}/{password}/societyProfile")
    public ResponseEntity<?> getSociety(@PathVariable String username,
                                        @PathVariable String password) {
        Society society = societyRepo.getSocietyBySocietyName(username);
        if (society == null) { 
            return ResponseEntity.status(404).body("Society not found");
        }
        if (!society.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Invalid password");
        }
        return ResponseEntity.ok(society);
    } 
    
    // check society is exist or not 
    @GetMapping("/{username}/societyProfileExistance")
    public ResponseEntity<?> getSocietyExistance(@PathVariable String username) {
        Society society = societyRepo.getSocietyBySocietyName(username);
        
        if (society == null) { 
            return ResponseEntity.status(404).body("Society not found");
        }
        
        return ResponseEntity.ok(society);
    }


    //To save Complaints
    @PostMapping("/{username}/addcomplaints")
    public ResponseEntity<?> addComplaints(
            @PathVariable String username,
            @RequestBody Complaint complaint) {
        try {
            // 1. Find the society
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("Society not found");
            } 
            safeHoodServices.saveComplaints(society, complaint);
            return ResponseEntity.status(201).body(complaint); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500) .body("Error adding complaint: " + e.getMessage());
        }
    }
    
 // To save Event Hall Booking
    @PostMapping("/{username}/bookhall")
    public ResponseEntity<?> addHallBooking(
            @PathVariable String username,
            @RequestBody EventHallBooking bookingHall) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("Society not found");
            }
            safeHoodServices.saveHallBooking(society, bookingHall);
            return ResponseEntity.status(201).body(bookingHall);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error while booking hall: " + e.getMessage());
        }
    }
    
 // ✅ Retrieve Event Hall Bookings Data
    @GetMapping("/{username}/getHallBookings")
    public ResponseEntity<?> getHallBookings(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            List<EventHallBooking> bookings = society.getEventHallBookings();

            if (bookings == null || bookings.isEmpty()) {
                return ResponseEntity.status(404).body("ℹ️ No event hall bookings found for this society.");
            }

            return ResponseEntity.ok(bookings);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("🚨 Error fetching event hall bookings: " + e.getMessage());
        }
    }



    // to update the event hall info 
    @PutMapping("/{username}/updateHallBooking/{hallID}")
    public ResponseEntity<?> updateHallBooking(@PathVariable String username,
                                               @PathVariable int hallID,
                                               @RequestBody EventHallBooking updatedBooking) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }
            boolean updated = safeHoodServices.updateHallBooking(society, hallID, updatedBooking);
            if (!updated) {
                return ResponseEntity.status(404).body("❌ Hall booking not found with ID: " + hallID);
            }
            return ResponseEntity.ok("✅ Hall booking updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error updating hall booking: " + e.getMessage());
        }
    }



    // Retrieve Complaints Data
    @GetMapping("/{username}/getComplaints")
    public List<Complaint> getComplaints(@PathVariable String username) {
        Society society = societyRepo.getSocietyBySocietyName(username);
        List<Complaint> complaint = society.getComplaint();
        return complaint;
    }
    
    @PatchMapping("/{username}/{complaintId}/complaints")
    public ResponseEntity<?> toggleComplaintStatus(
            @PathVariable String username,
            @PathVariable int complaintId) {
Society society = societyRepo.getSocietyBySocietyName(username);
        if (society == null) {
            return ResponseEntity.status(404).body("Society not found");
        }
        Complaint complaint = society.getComplaint()
                                     .stream()
                                     .filter(c -> c.getComplaint_Id() == complaintId) // use '==' for int
                                     .findFirst()
                                     .orElse(null);
        if (complaint == null) {
            return ResponseEntity.status(404).body("Complaint not found");
        }
        String currentStatus = complaint.getComplaint_Status();
        if ("pending".equalsIgnoreCase(currentStatus)) {
            complaint.setComplaint_Status("Resolved");
        } else {
            complaint.setComplaint_Status("Pending");
        }
        societyRepo.save(society);
        return ResponseEntity.ok(complaint);
    }


 // Get Events of a Society
    @GetMapping("/{username}/getEvents")
    public ResponseEntity<?> getEvents(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            List<Events> events = society.getEvent();

            if (events == null || events.isEmpty()) {
                return ResponseEntity.status(204).body("⚠️ No events found for this society");
            }

            return ResponseEntity.ok(events);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error fetching events: " + e.getMessage());
        }
    }
    
    


    // Retrieve Guard Data
    @GetMapping("/{username}/getGuard")
    public List<Guard> getGuard(@PathVariable String username) {
        Society society = societyRepo.getSocietyBySocietyName(username);
        List<Guard> guard = society.getGuard();
        return guard;
    }

    // Retrieve Notice Data
 // Get Notices
    @GetMapping("/{username}/getNotice")
    public ResponseEntity<?> getNotice(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            List<Notice> notices = society.getNotice();

            if (notices == null || notices.isEmpty()) {
                return ResponseEntity.status(204).body("ℹ️ No notices available for this society");
            }

            return ResponseEntity.ok(notices);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error retrieving notices: " + e.getMessage());
        }
    }


    // Retrieve User Data
    @GetMapping("/{username}/getUser")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("Society not found");
            }

            List<User> users = society.getUser();  

            if (users == null || users.isEmpty()) {
                return ResponseEntity.status(404).body("No users found for this society");
            }

            return ResponseEntity.ok(users);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching users: " + e.getMessage());
        }  
    }
    
    @GetMapping("/{societyUsername}/user/{userId}")
    public ResponseEntity<?> getUserById(
            @PathVariable String societyUsername,
            @PathVariable int userId) {
        try {
            // 1️⃣ Find the society by username
            Society society = societyRepo.getSocietyBySocietyName(societyUsername);
            if (society == null) {
                return ResponseEntity.status(404).body("Society not found");
            }

            // 2️⃣ Find the user by ID
            User user = userRepo.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            // 3️⃣ Check if this user belongs to the same society
            if (user.getSociety() == null || 
                user.getSociety().getSociety_Id() != society.getSociety_Id()) {
                return ResponseEntity.status(403).body("User does not belong to this society");
            }

            // 4️⃣ Return user details
            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching user: " + e.getMessage());
        }
    }


 // Get Parking Slots
    @GetMapping("/{username}/getParking")
    public ResponseEntity<?> getParking(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            List<Parking> parkingSlots = society.getParkingSlots();

            if (parkingSlots == null || parkingSlots.isEmpty()) {
                return ResponseEntity.status(204).body("ℹ️ No parking slots available for this society");
            }

            return ResponseEntity.ok(parkingSlots);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error retrieving parking slots: " + e.getMessage());
        }
    }

    

    @GetMapping("/payment-record/{username}/{userId}")
    public ResponseEntity<?> getPaymentRecords(
            @PathVariable String username,
            @PathVariable int userId) {

        // 🔍 Validate society
        Society society = societyRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Society not found"));

        // 🔍 Validate user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        // 🔒 Validate relation
        if (user.getSociety().getSociety_Id() != society.getSociety_Id()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User does not belong to this society");
        }

        // 🔥 Fetch only top 12 records
        List<PaymentRecordDTO> records = paymentRecordRepo.getTopPayments(
                username,
                userId,
                PageRequest.of(0, 12)   // LIMIT 12
        );

        if (records.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No payment records found");
        }

        return ResponseEntity.ok(records);
    }
    
    
    
    
}


