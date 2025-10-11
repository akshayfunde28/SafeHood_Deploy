//package com.SafeHood.Security;
//
//import java.nio.file.AccessDeniedException;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//@ControllerAdvice
//public class GlobalExceptionHandeler {
//	
//	
//	@ExceptionHandler(ReservationApiException.class)
//	public ResponseEntity<ErrorDetails> handelReservationApiException(
//			ReservationApiException exception , 
//			WebRequest request 
//			){
//		
//		final ErrorDetails errorDetails = new ErrorDetails(); 
//		errorDetails.setErrorMessage(exception.getLocalizedMessage());
//		errorDetails.setErrorCode(exception.getStatus().value());
//		errorDetails.setTimestamp(System.currentTimeMillis());
//		errorDetails.setDevErrorMessage(request.getDescription(false));
//		return new ResponseEntity(errorDetails,HttpStatus.BAD_REQUEST);
//	}
//
//	
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<ErrorDetails> handelAccessDenideException(
//			AccessDeniedException exception , 
//			WebRequest request 
//			){
//		
//		final ErrorDetails errorDetails = new ErrorDetails(); 
//		errorDetails.setErrorMessage(exception.getLocalizedMessage());
//		errorDetails.setTimestamp(System.currentTimeMillis());
//		errorDetails.setDevErrorMessage(request.getDescription(false));
//		return new ResponseEntity(errorDetails,HttpStatus.UNAUTHORIZED);
//	}
//	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorDetails> handelGlobalException(
//			Exception exception , 
//			WebRequest request 
//			){
//		
//		final ErrorDetails errorDetails = new ErrorDetails(); 
//		errorDetails.setErrorMessage(exception.getLocalizedMessage());
//		errorDetails.setTimestamp(System.currentTimeMillis());
//		errorDetails.setDevErrorMessage(request.getDescription(false));
//		return new ResponseEntity(errorDetails,HttpStatus.UNAUTHORIZED);
//	}
//
//}
