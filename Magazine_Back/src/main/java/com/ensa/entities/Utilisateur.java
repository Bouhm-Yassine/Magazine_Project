package com.ensa.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Utilisateur {
	@Id 
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	// @JsonIgnore
	private String password;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreation;

	@ManyToMany(fetch = FetchType.EAGER)
	// @JsonIgnore
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JsonIgnore
    @JoinColumn(name = "intervenant_id")
    private Intervenant intervenant;
	
	public Utilisateur() throws ParseException {
		super();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
	    String formattedDate= dateFormat.format(date);
	    
	    this.dateCreation = dateFormat.parse(formattedDate);	    
	}
	
	

}
