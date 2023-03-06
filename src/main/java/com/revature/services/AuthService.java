package com.revature.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.models.User;

@Service
public class AuthService {

	private final UserService userService;
	
	public AuthService(UserService userService) {
		this.userService = userService;
	}
	
	public Optional<User> findByCredentials(String email, String password) { // login method
		return userService.findByCredentials(email, password);
	}
	
	public User register(User user) {
		user.setRole("employee"); // automatically set a newly registered user as an employee
		return userService.save(user);
	}
	
	public Optional<User> findByEmail(String email) {
		return userService.findByEmail(email);
	}
}
