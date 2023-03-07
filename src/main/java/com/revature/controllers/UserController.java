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

import com.revature.annotations.AuthRestriction;
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
	
	@Authorized
	@GetMapping("{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		
		Optional<User> optional = userService.findByEmail(email);

		if(!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(optional.get());
	}
	
	@Authorized(value = AuthRestriction.Admin) // Only admins can change the roles of registered users
	@PatchMapping("/change/{email}")
	public ResponseEntity<Void> changeRole(@PathVariable String email) { 
		Optional<User> optional = userService.findByEmail(email);
		
		if(!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		User user = optional.get();
		
		if (optional.get().getRole().equals("employee")) {
			user.setRole("manager");
		} else {
			user.setRole("employee");
		}
		
		userService.changeRole(user);
		
		return ResponseEntity.status(200).build();
	}
	
}
