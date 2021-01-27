package com.ensa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class Role {

	@Id @GeneratedValue
	private Long id;
	private String role;
	
	public Role(String role) {
		super();
		this.role = role;
	}
	
}
