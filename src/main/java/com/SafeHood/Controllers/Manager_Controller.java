 

package com.SafeHood.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.Guard;
import com.SafeHood.Entities.Guest;
import com.SafeHood.Entities.Notice;
import com.SafeHood.Entities.Parking;
import com.SafeHood.Entities.SOS_Alert;
import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.User;
import com.SafeHood.Repository.EventRepo;
import com.SafeHood.Repository.GuardRepo;
import com.SafeHood.Repository.NoticeRepo;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Repository.UserRepo;
import com.SafeHood.Services.SafeHoodServices;

@RestController
@RequestMapping("/safeHood")
public class Manager_Controller {
 
    @Autowired
    SafeHoodServices safeHoodServices;
    @Autowired
    SocietyRepo societyRepo;
    @Autowired
	private UserRepo userRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private GuardRepo guardRepo;
    
    // add Society or Register society
    @PostMapping("/register/Society")
    public ResponseEntity<Void> addSociety(@RequestBody Society society) {
        societyRepo.save(society);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Add Notice
 // Add Notice
    @PostMapping("/{username}/addNotice")
    public ResponseEntity<?> addNotice(@PathVariable String username, @RequestBody Notice notice) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            // Save notice with society
            safeHoodServices.saveNotice(society, notice);

            return ResponseEntity.ok("✅ Notice added successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error adding notice: " + e.getMessage());
        }
    }
    
    // delete notice 
    @DeleteMapping("/{username}/deleteNotice/{noticeId}")
    public ResponseEntity<?> deleteNotice(
            @PathVariable String username,
            @PathVariable int noticeId) {
        try {
            // Find the society by username
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            // Find the notice by ID
            Optional<Notice> noticeOptional = noticeRepo.findById(noticeId);
            if (noticeOptional.isEmpty()) {
                return ResponseEntity.status(404).body("⚠️ Notice not found");
            }

            Notice notice = noticeOptional.get();

            // Check that notice belongs to this society
            if (notice.getSociety().getSociety_Id() != society.getSociety_Id()) {
                return ResponseEntity.status(403).body("🚫 Notice does not belong to this society");
            }

            // Delete the notice
            noticeRepo.delete(notice);
            return ResponseEntity.ok("✅ Notice deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error deleting notice: " + e.getMessage());
        }
    }


    
    //Add Event
    @PostMapping("/{username}/addEvent")
    public ResponseEntity<?> addEvent(
            @PathVariable String username,
            @RequestBody Events event) {

        try {
            // 1. Find the society
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("Society not found");
            }

            // 2. Pass event to service to save
            safeHoodServices.saveEvents(society, event);

            // 3. Return success response
            return ResponseEntity.status(201).body(event);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                                 .body("Error adding event: " + e.getMessage());
        }
    }

    // Delete Event by ID
    @DeleteMapping("/{username}/deleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable String username, @PathVariable int eventId) {
        try {
            // Step 1: Find the society by username
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }
            // Step 2: Find the event by ID
            Optional<Events> eventOpt = eventRepo.findById(eventId);
            if (eventOpt.isEmpty()) {
                return ResponseEntity.status(404).body("⚠️ Event not found with ID: " + eventId);
            }
            Events event = eventOpt.get();
            // Step 3: Check if the event belongs to the same society
            if (event.getSociety().getSociety_Id() != society.getSociety_Id()) {
                return ResponseEntity.status(403).body("🚫 Event does not belong to this society");
            }
            // Step 4: Delete the event
            eventRepo.delete(event);

            return ResponseEntity.ok("✅ Event deleted successfully: " + eventId);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error deleting event: " + e.getMessage());
        }
    }

    
    // add parking Slots 
    @PostMapping("/{username}/addParking")
    public ResponseEntity<?> addParking(@PathVariable String username, @RequestBody Parking parking) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            // Save parking with society
            safeHoodServices.saveParking(society, parking);

            return ResponseEntity.ok("✅ Parking slot added successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error adding parking slot: " + e.getMessage());
        }
    }
    
    @PutMapping("/{username}/updateParking/{slotId}")
    public ResponseEntity<?> updateParking(@PathVariable String username,
                                           @PathVariable int slotId,
                                           @RequestBody Parking updatedParking) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            boolean updated = safeHoodServices.updateParking(society, slotId, updatedParking);

            if (!updated) {
                return ResponseEntity.status(404).body("❌ Parking slot not found with ID: " + slotId);
            }

            return ResponseEntity.ok("✅ Parking slot updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error updating parking slot: " + e.getMessage());
        }
    }




   
 // Add Guard
    @PostMapping("/{username}/addGuard")
    public ResponseEntity<?> addGuard(@PathVariable String username, @RequestBody Guard guard) { 
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            // Save guard with society
            safeHoodServices.saveGuard(society, guard);

            return ResponseEntity.ok("✅ Guard added successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error adding guard: " + e.getMessage());
        }
    }
    
    // delete thr guard 
    @DeleteMapping("/{username}/deleteGuard/{guardId}")
    public ResponseEntity<?> deleteGuard(
            @PathVariable String username,
            @PathVariable int guardId) {
        try {
            // Find the society by username
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("❌ Society not found for username: " + username);
            }

            // Find the guard by ID
            Optional<Guard> guardOptional = guardRepo.findById(guardId);
            if (guardOptional.isEmpty()) {
                return ResponseEntity.status(404).body("⚠️ Guard not found");
            }

            Guard guard = guardOptional.get();

            // Check if guard belongs to the same society
            if (guard.getSociety().getSociety_Id() != society.getSociety_Id()) {
                return ResponseEntity.status(403).body("🚫 Guard does not belong to this society");
            }

            // Delete the guard
            guardRepo.delete(guard);
            return ResponseEntity.ok("✅ Guard deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("🚨 Error deleting guard: " + e.getMessage());
        }
    }



    // SOS alert Number Add
    @PostMapping("/{username}/addSos")
    public ResponseEntity<?> addSos(@PathVariable String username, @RequestBody SOS_Alert sos) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Society not found for username: " + username);
            }

            if (sos == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Number  cannot be null");
            }

            safeHoodServices.saveSos(society, sos);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("SOS Number  added successfully");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding SOS alert: " + e.getMessage());
        }
    }


    //Add User or member
    @PostMapping("/{username}/addUser")
    public ResponseEntity<?> addUser(@PathVariable String username, @RequestBody User user) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Society not found with username: " + username);
            }

            safeHoodServices.saveUser(society, user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User added successfully to society: " + username);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding user: " + e.getMessage());
        }
    }
    // delete Resident 
    @DeleteMapping("/{username}/deleteResident/{userId}")
    public ResponseEntity<?> deleteResident(
            @PathVariable String username,
            @PathVariable int userId) {
        try {
            // Find the society by username
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(404).body("Society not found");
            }

            // Find the user by ID
            Optional<User> userOptional = userRepo.findById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Resident not found");
            }

            User user = userOptional.get();

            // Ensure the user belongs to this society
            if (user.getSociety().getSociety_Id() != society.getSociety_Id()) {
                return ResponseEntity.status(400).body("Resident does not belong to this society");
            }
            // Delete the user
            userRepo.delete(user);
            return ResponseEntity.ok("Resident deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting resident: " + e.getMessage());
        }
    }



    // Retrieve Guest Data
    @GetMapping("/{username}/getGuest")
    public ResponseEntity<?> getGuest(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);
            
            if (society == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Society with username '" + username + "' not found");
            }
            
            List<Guest> guest = society.getGuest();
            return ResponseEntity.ok(guest);    
            
        } catch (Exception e) { 
            // Log the exception for debugging
            e.printStackTrace();
            
            // Return internal server error with message
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching guests");
        }
    }
 

    // Retrieve SOS number Data
    @GetMapping("/{username}/getSos")
    public ResponseEntity<?> getSos(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Society not found for username: " + username);
            }

            List<SOS_Alert> sos = society.getSos();

            if (sos == null || sos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No SOS alerts found for society: " + username);  
            }

            return ResponseEntity.ok(sos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching SOS alerts: " + e.getMessage());
        }
    }

}

