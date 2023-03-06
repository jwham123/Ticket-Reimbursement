package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket {

	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private float amount;
	
	@Column
	private String status;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	@JsonBackReference
	private User user;
}
