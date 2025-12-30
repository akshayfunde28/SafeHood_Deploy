package com.SafeHood.Controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SafeHood.Entities.Guest;
import com.SafeHood.Entities.GuestParking;
import com.SafeHood.Entities.Society;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Services.SafeHoodServices;

@RestController
@RequestMapping("/safeHood/{username}")
public class watchManController { 

    @Autowired   
    SafeHoodServices safeHoodServices;   
    @Autowired
    SocietyRepo societyRepo;
    
 // ✅ Add Guest
    @PostMapping("/addGuest")
    public ResponseEntity<String> addGuest(@PathVariable String username, @RequestBody Guest guest) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ Society not found for username: " + username);
            }

            safeHoodServices.saveGuest(society, guest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("✅ Guest added successfully.");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("⚠️ Invalid guest data or constraint violation: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("🚨 Error while adding guest: " + e.getMessage());
        }
    }

    
    @PostMapping("/addGuestParking")
    public ResponseEntity<String> addGuestParking(@PathVariable String username, @RequestBody GuestParking guestParking) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);
            if (society == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Society not found for username: " + username);
            }
            safeHoodServices.saveGuestParking(society, guestParking);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Guest parking added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding guest parking: " + e.getMessage());
        }
    }

    // ✅ Get All Guest Parking
    @GetMapping("/getGuestParkings")
    public ResponseEntity<?> getGuestParkings(@PathVariable String username) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username);

            if (society == null) {
                return ResponseEntity.status(404)
                        .body("❌ Society not found for username: " + username);
            }

            List<GuestParking> guestParkings = society.getGuestParking();

            if (guestParkings == null || guestParkings.isEmpty()) {
                return ResponseEntity.status(404)
                        .body("ℹ️ No guest parking records found for this society.");
            }

            return ResponseEntity.ok(guestParkings);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("🚨 Error fetching guest parking records: " + e.getMessage());
        }
    }


 // ✅ Update Guest Parking (e.g., mark exit or change status)
    @PutMapping("/updateGuestParking/{parkingId}")
    public ResponseEntity<String> updateGuestParking(
            @PathVariable String username,
            @PathVariable int parkingId,
            @RequestBody GuestParking updatedParking) {
        try {
            Society society = societyRepo.getSocietyBySocietyName(username); 
            if (society == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Society not found for username: " + username);
            }

            safeHoodServices.updateGuestParking(society, parkingId, updatedParking);
            return ResponseEntity.ok("✅ Guest parking updated successfully.");

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("⚠️ Guest parking not found: " + e.getMessage());
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("🚨 Error updating guest parking: " + e.getMessage());
        }
    }

    
}




