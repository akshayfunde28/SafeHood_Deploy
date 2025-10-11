package com.SafeHood.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
public class testController {

	
	@GetMapping("/")
	public String homePage() {
	    return "home" ;  
	}
	@GetMapping("/register")
	public String registerpage() {
	    return "registersociety" ;  
	}
	@GetMapping("/loginPage")
	public String loginPage() {
	    return "loginpage" ;  
	}

  
	@RequestMapping("/success")
	public String success() {
	    return "success" ;  
	}
	 

}
