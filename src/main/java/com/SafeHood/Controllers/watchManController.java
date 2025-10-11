package com.SafeHood.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SafeHood.Entities.Guest;
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
    
    // Add Guest
    @PostMapping("/addGuest")
    public void addGuest(@PathVariable String username, @RequestBody Guest guest) {
        Society society = societyRepo.getSocietyBySocietyName(username);
        safeHoodServices.saveGuest(society, guest);
    }
}








//package com.SafeHood.Controllers;
//
//import java.security.Principal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.SafeHood.Entities.Guest;
//import com.SafeHood.Entities.Society;
//import com.SafeHood.Repository.SocietyRepo;
//import com.SafeHood.Services.SafeHoodServices;
//
//@RestController
//@RequestMapping("/safeHood")
//public class watchManController {
//
//	
//	SafeHoodServices safeHoodServices;
//    @Autowired
//	SocietyRepo societyRepo;
//    // Add Guest
//    @PostMapping("/addGuest")
//	public void addGuest(@RequestBody Guest guest , Principal principal) {
//		String userName=principal.getName();
//		Society society=societyRepo.getSocietyBySocietyName(userName);
//		safeHoodServices.saveGuest(society, guest);
//	}
//	
//	
//	
//}
