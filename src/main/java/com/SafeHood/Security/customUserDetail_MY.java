//package com.SafeHood.Security;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.SafeHood.Entities.Society;
//
//public class customUserDetail  implements UserDetails{
//
//	
//	private Society  society ;
//
//	public customUserDetail(Society society) {
//		super();
//		this.society = society;
//	}
//
//	public customUserDetail() {
//		super();
//		 
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return society.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		return society.getUsername();
//	}
//	
//}
//package com;


