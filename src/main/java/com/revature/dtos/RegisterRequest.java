package com.revature.dtos;

import java.util.List;

import com.revature.models.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	private String email;
    private String password;
    private String username;
    private String role;
	
}
