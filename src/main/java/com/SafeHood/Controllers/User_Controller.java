








package com.SafeHood.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SafeHood.Entities.Complaint;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.Guard;
import com.SafeHood.Entities.Notice;
import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.User;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Services.SafeHoodServices;

@RestController
@RequestMapping("/safeHood")
public class User_Controller {
    
    @Autowired
    SafeHoodServices safeHoodServices;
    @Autowired
    SocietyRepo societyRepo;

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


    //getEvent Data
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

}








//package com.SafeHood.Controllers;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.SafeHood.Entities.Complaint;
//import com.SafeHood.Entities.Events;
//import com.SafeHood.Entities.Guard;
//import com.SafeHood.Entities.Notice;
//import com.SafeHood.Entities.Society;
//import com.SafeHood.Entities.User;
//import com.SafeHood.Repository.SocietyRepo;
//import com.SafeHood.Services.SafeHoodServices;
//
//@RestController
//@RequestMapping("/{username}")
//public class User_Controller {
//    
//    @Autowired
//    SafeHoodServices safeHoodServices;
//    @Autowired
//    SocietyRepo societyRepo;
//
//    // Society Profile
//    @GetMapping("/societyProfile")
//    public Society getSociety(@PathVariable String username) {
//        Society society = societyRepo.getSocietyBySocietyName(username);
//        return society;
//    }
//
//    //To save Complaints
//    @PostMapping("/addcomplaints")
//    public void addComplaints(@PathVariable String username, @RequestBody Complaint complaints) {
//        Society society = societyRepo.getSocietyBySocietyName(username);
//        safeHoodServices.saveComplaints(society, complaints);
//    }
//
//    // Retrieve Complaints Data
//    @GetMapping("/getComplaints")
//    public List<Complaint> getComplaints(@PathVariable String username) {
//        Society society = societyRepo.getSocietyBySocietyName(username);
//        List<Complaint> complaint = society.getComplaint();
//        return complaint;
//    }
//    
//    //Update Complaints !!!
//    //( in update only update status pending or resolve )--------------->>>>>>>>>>>>>>>0xox0<<<<<<<<<<<<----------------
//    // for update call save method that is addComplaints
//
//    //getEvent Data
//    @GetMapping("/getEvents")
//    public List<Events> getEvents(@PathVariable String username) {
//        Society society = societyRepo.getSocietyBySocietyName(username);
//        List<Events> events = society.getEvent();
//        return events;
//    }
//
//    // Retrieve Guard Data
//    @GetMapping("/getGuard")
//    public List<Guard> getGuard(@PathVariable String username) {
//        Society society = societyRepo.getSocietyBySocietyName(username);
//        List<Guard> guard = society.getGuard();
//        return guard;
//    }
//
//    // Retrieve Notice Data
//    @GetMapping("/getNotice")
//    public List<Notice> getNotice(@PathVariable String username) {
//        Society society = societyRepo.getSocietyBySocietyName(username);
//        List<Notice> Notice = society.getNotice();
//        return Notice;
//    }
//
//    // Retrieve User Data
//    @GetMapping("/getUser")
//    public List<User> getUser(@PathVariable String username) {
//        Society society = societyRepo.getSocietyBySocietyName(username);
//        List<User> user = society.getUser();
//        return user;
//    }
//}