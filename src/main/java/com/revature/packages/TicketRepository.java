package com.revature.packages;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

}
