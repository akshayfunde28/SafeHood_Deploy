package com.SafeHood.Controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SafeHood.DTO.GuestRequestDTO;
import com.SafeHood.DTO.PreApprovedGuestDTO;
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
    
 // ✅ send request to the resident to approve the guest 
@PostMapping("/request-guest-entry")
public ResponseEntity<?> requestGuestEntry(
        @RequestBody GuestRequestDTO guestDTO) {

    try {

        String response =
        		safeHoodServices.requestGuestEntry(guestDTO);

        return ResponseEntity.ok(response);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
    }
}

// pre-approved guest by resident 
@PostMapping("/pre-approve-guest")
public ResponseEntity<?> preApproveGuest(
        @RequestBody PreApprovedGuestDTO guestDTO) {

    try {

        String response =
                safeHoodServices.preApproveGuest(guestDTO);

        return ResponseEntity.ok(response);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
    }
}

// get guest list of particular resident [show in category pending , approved , pre-approved, rejected ]
@GetMapping("/resident-guest-list")
public ResponseEntity<?> getResidentGuestList(
        @RequestParam Integer residentId) {

    try {

        List<Guest> guestList =
                safeHoodServices.getResidentGuests(
                        residentId);

        return ResponseEntity.ok(guestList);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
    }
}

// update the guest status approved and reject by resident 
@PutMapping("/update-guest-status")
public ResponseEntity<?> updateGuestStatus(
        @RequestParam Integer guestId,
        @RequestParam String status) {

    try {

        String response =
                safeHoodServices.updateGuestStatus(
                        guestId,
                        status);

        return ResponseEntity.ok(response);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
    }
}

// get pre approved [NOT_ENTERED]guest list for society guard
@GetMapping("/pre-approved-guest-list")
public ResponseEntity<?> getPreApprovedGuestList(
        @PathVariable  String username) {

    try {

        List<Guest> guestList =
                safeHoodServices.getPreApprovedGuests(
                        username);

        return ResponseEntity.ok(guestList);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
    }
}
//get all PRE_APPROVED entered guest list of society for guard
@GetMapping("/pre-approved-entered-guests")
public ResponseEntity<?> getPreApprovedEnteredGuests(
     @PathVariable String username) {

 try {

     List<Guest> guestList =
             safeHoodServices
                     .getPreApprovedEnteredGuests(username);

     return ResponseEntity.ok(guestList);

 } catch (Exception e) {

     return ResponseEntity
             .status(500)
             .body(e.getMessage());
 }
}

// update the pre approved guest status with code verification by guard 
@PutMapping("/verify-preapproved-guest")
public ResponseEntity<?> verifyPreApprovedGuest(
        @RequestParam Integer guestId,
        @RequestParam String verificationCode) {

    try {

        String response =
                safeHoodServices.verifyPreApprovedGuest(
                        guestId,
                        verificationCode);

        return ResponseEntity.ok(response);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
    }
}
    
// get all approved guest list of society 
@GetMapping("/approved-guest-list")
public ResponseEntity<?> getApprovedGuestList(
        @PathVariable String username) {

    try {

        List<Guest> guestList =
                safeHoodServices
                        .getApprovedGuestList(
                                username);

        return ResponseEntity.ok(guestList);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
    }
}

// get all  guest of society sort by created time for manager 
@GetMapping("/all-society-guests")
public ResponseEntity<?> getAllSocietyGuests(
		@PathVariable String username) {

    try {

        List<Guest> guestList =
                safeHoodServices
                        .getAllSocietyGuests(
                                username);

        return ResponseEntity.ok(guestList);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(e.getMessage());
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




