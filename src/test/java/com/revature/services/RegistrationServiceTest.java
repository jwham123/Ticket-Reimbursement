package com.revature.services;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.models.User;
import com.revature.packages.UserRepository;

@SpringBootTest
public class RegistrationServiceTest {

	@Mock
	private UserRepository userRepo;
	
	@InjectMocks
	private UserService userService;
	
	//@InjectMocks
	//private AuthService authService;
	
	
		
	@Test
	public void testRegisterUser() {
		User user = new User("joewham14@gmail.com","password","jwham14","employee");
		when(userRepo.save(any(User.class))).thenReturn(user);
		//System.out.println(user.getEmail());
		
		User registeredUser = userService.save(user); // must use the service layer that is directly connected to the repository layer
		
		assertEquals("joewham14@gmail.com", registeredUser.getEmail());
		assertEquals("password",registeredUser.getPassword());
		assertEquals("jwham14",registeredUser.getUsername());
		assertEquals("employee",registeredUser.getRole());
		verify(userRepo, times(1)).save(any(User.class)); // verifies the user repo is called once
		
	}
	
	@Test
	public void testSuccessfulLogin() {
		String email = "joeywham@udel.edu";
		String password = "password123";
		User user = new User(email,password,"jwham14","employee");
		when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
		
		Optional<User> result = userService.findByCredentials(email,password);
		System.out.println(result.get().getEmail());
	}
	}

