package com.revature.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email"), 
		@UniqueConstraint(columnNames = "username")
})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String username;
	
	@Column
	private String role;
	@OneToMany(mappedBy="user",fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Ticket> tickets = new ArrayList<>();
	
	public User(int id, String email, String password, String username, String role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
	}
	
	public User(int id, String email, String password, String username) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
	}
	
	public User(String email, String password, String username, String role) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
	}
	
}
