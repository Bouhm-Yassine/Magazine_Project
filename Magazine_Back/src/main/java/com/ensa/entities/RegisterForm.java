package com.ensa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class RegisterForm {
	
	private String nom;
	private String email;
	private String username;
	private String password;
	private String repassword;
	

}
