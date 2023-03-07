package com.revature.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.revature.exceptions.InvalidRoleException;
import com.revature.exceptions.NotLoggedInException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(NotLoggedInException.class)
	public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request, NotLoggedInException notLoggedInException) {
		
		String errorMessage = "Must be logged in to perform this action";
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
	}
	
	@ExceptionHandler(InvalidRoleException.class)
	public ResponseEntity<Object> handleNotValidRoleException(HttpServletRequest request, InvalidRoleException notValidRoleException) {
		
		String errorMessage = "Must be logged in as an Admin to perform this action";
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
	}
}
