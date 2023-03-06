package com.revature.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revature.exceptions.UserAlreadyExistsException;

@ControllerAdvice
public class CustomExceptionHandler {
@ExceptionHandler(UserAlreadyExistsException.class)
public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
	return ResponseEntity.badRequest().body(ex.getMessage());
}
}
