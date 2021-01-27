package com.ensa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Intervenant {
	@Id 
	@GeneratedValue
	private Long id;
	private String nom;
	private String email;
	
	@OneToOne(mappedBy = "intervenant")
	private Utilisateur utilisateur;
	
	@OneToMany(mappedBy="intervenant")
	@JsonIgnore
	private List <Affectation> affectaions = new ArrayList<>();
	
	@OneToMany(mappedBy="intervenant")
	@JsonIgnore
	private List <Deposition> depositions = new ArrayList<>();
	
	public Intervenant() {
		super();
	}
	
	

}
