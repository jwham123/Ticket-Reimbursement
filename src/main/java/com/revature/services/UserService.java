package com.revature.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.models.User;
import com.revature.packages.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public Optional<User> findByCredentials(String email, String password) { // login method
        return userRepository.findByEmailAndPassword(email, password);
    }
	
    public User save(User user) {
    	Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
        	throw new UserAlreadyExistsException("Email already exists!");
        }
    
    	return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
    
    public User changeRole(User user) {
    	return userRepository.save(user);
    }
}
