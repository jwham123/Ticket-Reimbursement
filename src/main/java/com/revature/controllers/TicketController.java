package com.revature.controllers;

import java.util.List;
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
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.TicketService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class TicketController {

	private final TicketService ticketService;
	private final AuthService authService;
	private final UserService userService;
	
	public TicketController(TicketService ticketService, AuthService authService,
			UserService userService) {
		this.ticketService = ticketService;
		this.authService = authService;
		this.userService = userService;
	}
	
	@Authorized
	@PostMapping("/create")
	public ResponseEntity<Ticket> create(@RequestBody Ticket ticket, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ticket.setUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.save(ticket));
	}
	
	@Authorized // get your own tickets
	@GetMapping("/get")
	public ResponseEntity<List<Ticket>> getMyTickets(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return ResponseEntity.ok(ticketService.findByUserId(user.getId()));	
	}
	
	@Authorized // for managers/admin to view other employees/managers tickets
	@GetMapping("/getOthers")
	public ResponseEntity<List<Ticket>> getotherTickets(HttpSession session) {
		User user = (User) session.getAttribute("user");
		System.out.println(user.getId());
		return ResponseEntity.ok(ticketService.findOthers(user.getId()));	
	}
	
	@Authorized// for managers/admin to view other employees/managers tickets and their own
	@GetMapping("/getAll")
	public ResponseEntity<List<Ticket>> getAllTickets(HttpSession session) {
		User user = (User) session.getAttribute("user");
		System.out.println(user.getId());
		return ResponseEntity.ok(ticketService.findAll());	
	}
	
	@Authorized
	@GetMapping("{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
		
		Optional<Ticket> optional = ticketService.getTicketById(id);
		
		if(!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(optional.get());
	}
	
	@PatchMapping("/appDen")
	public ResponseEntity<Void> appDenTicket(@RequestBody Ticket ticket) {
		Optional<Ticket> optional = ticketService.getTicketById(ticket.getId());
		
		if(!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		optional.get().setStatus(ticket.getStatus());
		ticketService.appDeny(optional.get());
		return ResponseEntity.status(200).build();
		
	}
}
