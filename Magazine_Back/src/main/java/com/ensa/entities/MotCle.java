package com.ensa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class MotCle {
	
	@Id 
	@GeneratedValue
	private Long id;
	private String libelle;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name="id_article")
	private Article article;

}
