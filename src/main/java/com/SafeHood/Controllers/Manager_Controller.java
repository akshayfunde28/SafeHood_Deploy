 

package com.SafeHood.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SafeHood.Entities.Events;
import com.SafeHood.Entities.Guard;
import com.SafeHood.Entities.Guest;
import com.SafeHood.Entities.Notice;
import com.SafeHood.Entities.SOS_Alert;
import com.SafeHood.Entities.Society;
import com.SafeHood.Entities.User;
import com.SafeHood.Repository.SocietyRepo;
import com.SafeHood.Services.SafeHoodServices;

@RestController
@RequestMapping("/safeHood")
public class Manager_Controller {
 
    @Autowired
    SafeHoodServices safeHoodServices;
    @Autowired
    SocietyRepo societyRepo;
    
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


    // Add Guard
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









//package com.SafeHood.Controllers;
//
//import java.security.Principal;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.SafeHood.Entities.Events;
//import com.SafeHood.Entities.Guard;
//import com.SafeHood.Entities.Guest;
//import com.SafeHood.Entities.Notice;
//import com.SafeHood.Entities.SOS_Alert;
//import com.SafeHood.Entities.Society;
//import com.SafeHood.Entities.User;
//import com.SafeHood.Repository.SocietyRepo;
//import com.SafeHood.Services.SafeHoodServices;
//
//@RestController
//@RequestMapping("/safeHood")
//public class Manager_Controller {
//	
//	@Autowired
//	SafeHoodServices safeHoodServices;
//    @Autowired
//	SocietyRepo societyRepo;
//    @GetMapping("/dummy")
//  	public void dummy() {
//  	System.out.println(" hello ####");
//  		
//  	}
//	
//
//	// add Society    or Register society  
//	@PostMapping("/register/Society")
//	public ResponseEntity<Society> addSociety(@RequestBody Society society) {
////		society.setPassword(bCryptPasswordEncoder.encode(society.getPassword()));
//		societyRepo.save(society);
//		return ResponseEntity.ok(society);
//	}
//	
//	//Add  Notice 
//	    @PostMapping("/addNotice")
//	public void addNotice(@RequestBody Notice notice , Principal principal) {
//		String userName=principal.getName();
//		Society society=societyRepo.getSocietyBySocietyName(userName);
//		safeHoodServices.saveNotice(society, notice);
//		
//	}
//	 //Add  Event   
//	    @PostMapping("/addEvent")
//		public void addEvent(@RequestBody Events event , Principal principal) {
//			String userName=principal.getName();
//			Society society=societyRepo.getSocietyBySocietyName(userName);
//			safeHoodServices.saveEvents(society, event);
//		}
//	    
//	// Add Guard 
//	    @PostMapping("/addGuard")
//		public void addGuard(@RequestBody Guard guard , Principal principal) {
//			String userName=principal.getName();
//			Society society=societyRepo.getSocietyBySocietyName(userName);
//			safeHoodServices.saveGuard(society, guard);
//		}
//	    
//	   // SOS alert Number Add 
//	    @PostMapping("/addSos")
//		public void addSos(@RequestBody SOS_Alert sos , Principal principal) {
//			String userName=principal.getName();
//			Society society=societyRepo.getSocietyBySocietyName(userName);
//			safeHoodServices.saveSos(society, sos);
//		}
//
//	    //Add User or member 
//	    @PostMapping("/addUser")
//		public void addUser(@RequestBody User user , Principal principal) {
//			String userName=principal.getName();
//			Society society=societyRepo.getSocietyBySocietyName(userName);
//			safeHoodServices.saveUser(society, user);
//		}
//	    
//	    
//
//		// Retrieve Guest Data
//				@GetMapping("/getGuest")
//				public List<Guest> getNotice(Principal principal ){
//					String userName = principal.getName();
//					Society society=societyRepo.getSocietyBySocietyName(userName);
//					List<Guest> guest =	society.getGuest();
//					return guest ;
//				}
//				
//				// Retrieve SOS number Data
//				@GetMapping("/getSos")
//				public List<SOS_Alert> getSos(Principal principal ){
//					String userName = principal.getName();
//					Society society=societyRepo.getSocietyBySocietyName(userName);
//					List<SOS_Alert> sos =	society.getSos();
//					return sos ;
//				}
//	
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
