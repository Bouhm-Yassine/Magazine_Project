package com.ensa.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.engine.FetchStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
public @Data class Article {

	@Id 
	@GeneratedValue
	private Long id;
	private String titre;
	private String resume;
	
	@Lob
	private byte[] manuscrite;
	
	private String affiliation;
	private String decisionFinal;
	private String etat;
	
	
	@OneToMany(mappedBy="article", fetch = FetchType.EAGER)
	@JsonIgnore
	private List <Affectation> affectaions = new ArrayList<>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL)
	@JsonIgnore
	private List <Deposition> depositions = new ArrayList<>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL)
	@JsonIgnore
	private List <MotCle> motsCles = new ArrayList<>();
	
	public Article() {
		super();
	}
	
}
