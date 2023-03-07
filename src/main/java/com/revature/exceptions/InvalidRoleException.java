package com.revature.exceptions;

public class InvalidRoleException extends RuntimeException {

	public InvalidRoleException() {
	}
	
	public InvalidRoleException(String message) {
		super(message);
	}
	
}
