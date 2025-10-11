//package com.SafeHood.Security;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//public class securityConfig {
//	@Autowired
//	private JwtEntryPoint jwtEntryPoint;
//	@Autowired
//	private CustomUserDetailService customUserDetailService ;
//	@Autowired
//	private JwtAuthFilter jwtAuthFilter ;
//
//	
//	@Bean
//	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//	    http
//	        .csrf(csrf -> csrf.disable())
//	        .authorizeHttpRequests(auth -> auth
//	            .anyRequest().permitAll()  // Allow EVERYTHING temporarily
//	        )
//	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//	    
//	    // DON'T add the JWT filter for now
//	    // http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//	    
//	    return http.build();
//	}
//	
//	
////	@Bean
////	  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
////	    http
////	        // Disable CSRF since you're using JWT (not sessions)
////	        .csrf(csrf -> csrf.disable())
////
////	        // Authorize all HTTP requests
////	        .authorizeHttpRequests(auth -> auth
////	        		.requestMatchers(HttpMethod.POST, "/safeHood/signin/addSociety").permitAll()
////	            .anyRequest().authenticated() 
////	        )
////	        .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint))
////	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////	    	http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
////	    return http.build();
////	}
//
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
//		return config.getAuthenticationManager();
//	}
//
//}
