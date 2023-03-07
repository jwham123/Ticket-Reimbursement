package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.packages.TicketRepository;

@Service
public class TicketService {

	private TicketRepository ticketRepository;
	
	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}
	
	public Ticket save(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}
	
	public List<Ticket> findByUserId(int id) {
		return ticketRepository.findByUserId(id);
	}
	
	public List<Ticket> findOthers(int id) {
		List<Ticket> allTicks = ticketRepository.findAll();
		List<Ticket> yourTicks = ticketRepository.findByUserId(id);
		
		for (int i = 0; i < yourTicks.size(); i++) {
			if (allTicks.contains(yourTicks.get(i))) {
				allTicks.remove(yourTicks.get(i));
			}
		}
		return allTicks;
	}
	
	public Optional<Ticket> getTicketById(int id) {
		return ticketRepository.findById(id);
	}
	
	public Ticket appDeny(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
}
