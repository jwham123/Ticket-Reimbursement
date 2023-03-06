package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.Ticket;
import com.revature.packages.TicketRepository;

@Service
public class TicketService {

	private TicketRepository ticketRepository;
	
	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}
	
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}
	
	public Ticket save(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
}
