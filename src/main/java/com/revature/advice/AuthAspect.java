package com.revature.advice;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.revature.annotations.AuthRestriction;
import com.revature.annotations.Authorized;
import com.revature.exceptions.InvalidRoleException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;


@Aspect
@Component
public class AuthAspect {

	// autowires a proxy object for the request
	// a proxy object's primary objective is to control the creation of 
	// and access to the real object it represents
	private final HttpServletRequest req;
	
	public AuthAspect(HttpServletRequest req) {
		this.req = req;
	}	
	// This advice will execute around any method annotated with @Authorized
	// If the user is not logged in, an exception will be thrown and handled
	// Otherwise, the original method will be invoked as normal
	// In order to expand upon this, you just need to add additional values to the
	// AuthRestriction enum
	// Examples might be "Manager" or "Customer" along with suitable Role values in the
	// User class
	// Then this method can be expanded to throw exceptions if the user does not have
	// the matching role
	// Example:
    // User loggedInUser = (User) session.getAttribute("user");
    // Role userRole = loggedInUser.getRole();
    // if(authorized.value().equals(AuthRestriction.Manager) && !Role.Manager.equals(userRole)) {
    //     throw new InvalidRoleException("Must be logged in as a Manager to perform this action");
    // }
    // Then the RestExceptionHandler class can be expanded to include
    // @ExceptionHandler(InvalidRoleException.class)
    // which should return a 403 Forbidden such as:
    // String errorMessage = "Missing required role to perform this action";
    // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
	
		@Around("@annotation(authorized)")
	    public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {

	        HttpSession session = req.getSession(); // Get the session (or create one)
	        
	        //System.out.println("User Role: "+ session.getAttribute("user"));
	        
	        // If the user is not logged in
	        if(session.getAttribute("user") == null) {
	            throw new NotLoggedInException("Must be logged in to perform this action");
	        }
	        
	        // For changing user role, the logged in user must have the status "admin" to perform this task
	        User loggedInUser = (User) session.getAttribute("user");
	        if (authorized.value().equals(AuthRestriction.Admin) && !loggedInUser.getRole().equals("admin")) {
	        	throw new InvalidRoleException("Must be logged in as an Admin to perform this action");
	        }

	        return pjp.proceed(pjp.getArgs()); // Call the originally intended method
	}
}
