//package com.SafeHood.Security;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//@Component
//public class JwtAuthFilter  extends OncePerRequestFilter{
//	
//	@Autowired
//	private JwtTokenProvider jwtTokenProvider ; 
//	
//	@Autowired
//	private CustomUserDetailService customUserDetailService ;
//	
//
//	@Override                                                                      // this request contains the token 
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		 String path = request.getServletPath();
//		 

//		String token = getTokenFromRequest(request);
//		 
//		
//		if(StringUtils.hasText(token) && jwtTokenProvider.validToken(token)) {
//				String userName = jwtTokenProvider.getUserNameString(token);
//				UserDetails userDetails	= customUserDetailService.loadUserByUsername(userName);
//	
//		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
//				new UsernamePasswordAuthenticationToken( //this stores the info of user like role and all and tell to spring security 
//						userDetails,
//						null,
//						userDetails.getAuthorities()
//				);
//		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));// extra authentication provided to the spring security  
//			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//		 	}
//		filterChain.doFilter(request, response);
//	
//	}
//
//
//	private String getTokenFromRequest(HttpServletRequest request) {
//		String bearerToken = request.getHeader("Authorization");
//		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//			return bearerToken.substring(7);
//		}
//		return null;
//	}
//
//}
