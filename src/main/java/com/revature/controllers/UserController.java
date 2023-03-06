package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class UserController {

	private final AuthService authService;
	private final UserService userService;
	
	public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
	
	@PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());
        //System.out.println(optional.get().getEmail());
        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
    }
	
	@PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }
	
	@PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
       
		
		User created = new User(0,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getUsername(),
                registerRequest.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
    }
	
	@GetMapping("{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		
		Optional<User> optional = userService.findByEmail(email);
		if(!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(optional.get());
	}
	
//	@Authorized // Annotation that allows only logged in users to perform this function
//	@PatchMapping("/roleChange")
//	public ResponseEntity<Void> changeRole(@RequestBody String email, HttpSession session) { 
//		// If you are logged in as the admin, you have the ability to change the role of an employee/manager
//		// This function simply switches the role, i.e. if they are a manager, they will become an employee and vice versa
//		
//		User admin = (User) session.getAttribute("user"); 
//		System.out.println(admin.getRole());
//		
//		if (admin.getRole().equals("admin")) {
//			System.out.println("You are an admin");
//			
//		} else {
//			System.out.println("You are not an admin");
//		}
//		return ResponseEntity.status(400).build();
//	}
	
}
