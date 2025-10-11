//package com.SafeHood.Security;
//
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.Keys;
//
//@Component
//public class JwtTokenProvider {
//	
//	
//	@Value("${app.jwt.secret}")
//	private  String jwtSecrateKey ;
//	
//	@Value("${app.jwt.expiration-milliseconds}")
//	private Long expiration ; 
//	// spring security passes the user name in this authentication object internally from this we can retrive the data 
//	public String generateToken(Authentication authentication) {
//		String userName  = authentication.getName();
//		Date expireDate = new Date(new Date().getTime()+expiration);
//		return Jwts.builder()
//				.setSubject(userName)
//				.setIssuedAt(new Date())
//				.setExpiration(expireDate)
//				.signWith(key())
//				.compact();
//		
//	}
//	
//	public String getUserNameString(String token) {
//		Claims claims = Jwts.parser()// this parser contains the jwt credentila information like token username and all 
//				.setSigningKey(key())
//				.build()
//				.parseClaimsJws(token)
//				.getBody();
//				return claims.getSubject();
//	}
//	public boolean  validToken(String token) {
//		try {
//			Jwts.parser()
//			.setSigningKey(key())
//			.build()
//			.parse(token);
//			return true ;
//		} catch (MalformedJwtException e) {
//			throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Invalid Token ");
//		}
//		catch (ExpiredJwtException e) {
//			throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Invalid Token ");
//		}
//		catch (UnsupportedJwtException e) {
//			throw new ReservationApiException(HttpStatus.BAD_REQUEST, "unsupported Token ");
//		}
//		catch (IllegalArgumentException e) {
//			throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Invalid arguments ");
//		}
//		
//	}
//	
//	private Key key() { // this use to create the key 
//	    byte[] decodedKey = Base64.getDecoder().decode(jwtSecrateKey);
//	    return Keys.hmacShaKeyFor(decodedKey);
//	}
//
//}
