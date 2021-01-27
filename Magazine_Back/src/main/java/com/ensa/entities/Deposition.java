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
public class Deposition {
	
	@Id 
	@GeneratedValue
	private Long id;
	private String status;
	@Temporal(TemporalType.DATE)
	private Date dateDepot;
	
	@ManyToOne
    @JoinColumn(name="id_intervenant")
	private Intervenant intervenant;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="id_article")
	private Article article;
	
	public Deposition() throws ParseException {
		super();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
	    String formattedDate= dateFormat.format(date);
	    
	    this.dateDepot = dateFormat.parse(formattedDate);	     
	}
	

}
