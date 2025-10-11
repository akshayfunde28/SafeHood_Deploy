//package com.SafeHood.Security;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.SafeHood.Entities.Society;
//import com.SafeHood.Repository.SocietyRepo;
// 
//
//@Service
//public class CustomUserDetailService  implements UserDetailsService { 
//	
//@Autowired
//	private SocietyRepo societyRepo ; 
//	 
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		Society society = societyRepo.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User Is Not Exiest "));
//		
//		return  User.builder() 
//				.username(society.getUsername())
//				.password(society.getPassword())
//				.build();
//	}
//
//}
