//// package com.SafeHood.Security;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.core.userdetails.UserDetailsService;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
////
////import com.SafeHood.Entities.Society;
////import com.SafeHood.Repository.SocietyRepo;
////
////public class customUserDetailServices implements UserDetailsService {
////		@Autowired
////		private SocietyRepo societyRepo;
////	
////	@Override
////	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////		
////		Society society=societyRepo.getSocietyBySocietyName(username);
////		if(society == null) {
////			throw new UsernameNotFoundException("username in not Found !!");
////		}
////			customUserDetail customUserDetail = new customUserDetail(society);
////		return customUserDetail;
////	}
////
////}
////package com;
//
//
