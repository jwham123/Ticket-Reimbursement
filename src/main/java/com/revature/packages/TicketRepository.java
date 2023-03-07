package com.revature.packages;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Ticket;
import com.revature.models.User;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	List<Ticket> findByUserId (int id);
}
