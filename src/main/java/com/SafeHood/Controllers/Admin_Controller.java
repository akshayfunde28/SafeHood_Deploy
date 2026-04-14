package com.SafeHood.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SafeHood.Entities.Admin_Info;
import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.SocietyDTO;
import com.SafeHood.Repository.AdminRepo;
import com.SafeHood.Repository.PaymentDetailsRepo;
import com.SafeHood.Repository.PaymentRecordRepo;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Repository.UserRepo;

@RestController
@RequestMapping("/safeHood/admin")
public class Admin_Controller {
 

    @Autowired
    private SocietyRepo societyRepo;
    @Autowired
    private PaymentDetailsRepo paymentDetailsRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PaymentRecordRepo paymentRecordRepo;
    @Autowired
    private AdminRepo adminRepo ; 



    // ✅ 1. Get all societies count and data 
    @GetMapping("/societies")
    public Object getAllSocieties() {

        List<SocietyDTO> societies = societyRepo.getAllSocietyDTO();

        return new Object() {
            public int count = societies.size();
            public List<SocietyDTO> data = societies;
        };
    }

//    // ✅ 2. Get society by username
//    @GetMapping("/society/{username}")
//    public Society getSocietyByUsername(@PathVariable String username) {
//        return societyRepo.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("Society not found"));
//    }

    // ✅ 3. Delete society by username
    @DeleteMapping("/society/{username}")
    public String deleteSocietyByUsername(@PathVariable String username) {

        Society society = societyRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        societyRepo.delete(society);  // 🔥 This will trigger cascade + orphanRemoval

        return "Society deleted successfully";
    }
    
    //✅ 4 update the society 
    @PutMapping("/society/update/{username}")
    public ResponseEntity<String> updateSociety(@PathVariable String username,
                                                @RequestBody Society updatedSociety) {
        try {

            Optional<Society> optionalSociety = societyRepo.findByUsername(username);

            if (!optionalSociety.isPresent()) {
                return new ResponseEntity<>("Society not found with username: " + username,
                        HttpStatus.NOT_FOUND);
            }

            Society existingSociety = optionalSociety.get();
            if (updatedSociety.getStatus() != null) {
                existingSociety.setStatus(updatedSociety.getStatus());
            }

            societyRepo.save(existingSociety);

            return new ResponseEntity<>("Society updated successfully ✅", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating society: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ✅ 1. CREATE ADMIN
    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody Admin_Info admin) {
        try {
            if (adminRepo.count() > 0) {
                return new ResponseEntity<>("Admin already exists (only one allowed)",
                        HttpStatus.BAD_REQUEST);
            }

            Admin_Info savedAdmin = adminRepo.save(admin);
            return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating admin: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ 2. GET ADMIN (ONLY ONE)
    @GetMapping("/get")
    public ResponseEntity<?> getAdmin() {
        try {
            List<Admin_Info> admins = adminRepo.findAll();

            if (admins.isEmpty()) {
                return new ResponseEntity<>("No admin found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(admins.get(0), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error while fetching admin: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ 3. UPDATE ADMIN (BY NAME)
    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateAdmin(@PathVariable String name,
                                         @RequestBody Admin_Info updatedAdmin) {
        try {
            Optional<Admin_Info> optionalAdmin = adminRepo.findByAdminName(name);

            if (!optionalAdmin.isPresent()) {
                return new ResponseEntity<>("Admin not found with name: " + name,
                        HttpStatus.NOT_FOUND);
            }

            Admin_Info existingAdmin = optionalAdmin.get();

            // 🔄 Update all fields
            existingAdmin.setAdminName(updatedAdmin.getAdminName());
            existingAdmin.setAdminEmail(updatedAdmin.getAdminEmail());
            existingAdmin.setAdminPassword(updatedAdmin.getAdminPassword());
            existingAdmin.setPhoneNo(updatedAdmin.getPhoneNo());

            Admin_Info savedAdmin = adminRepo.save(existingAdmin);

            return new ResponseEntity<>(savedAdmin, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating admin: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ 4. DELETE ALL ADMINS (TRUNCATE)
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllAdmins() {
        try {
            adminRepo.deleteAll();
            return new ResponseEntity<>("All admins deleted successfully",
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting admins: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}