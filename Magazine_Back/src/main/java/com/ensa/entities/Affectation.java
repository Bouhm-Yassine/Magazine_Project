package com.ensa.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class Affectation {

	@Id 
	@GeneratedValue
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date dateAffectation;
	private String decision;
	private String commentaire;
	
	@ManyToOne
    @JoinColumn(name="id_intervenant")
	private Intervenant intervenant;
	
	@ManyToOne
    @JoinColumn(name="id_article")
	private Article article;

	public Affectation() throws ParseException {
		super();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
	    String formattedDate= dateFormat.format(date);
	    
	    this.dateAffectation = dateFormat.parse(formattedDate);	     
	}
	
	
}
