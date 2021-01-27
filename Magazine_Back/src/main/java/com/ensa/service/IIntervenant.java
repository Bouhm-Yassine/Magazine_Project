package com.ensa.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;

@Component
public interface IIntervenant {

	public Intervenant save(Intervenant objet);
	public Intervenant getById(Long id);
	public List<Intervenant> getAll();
	public List<Intervenant> getAuteurs();
	public List<Intervenant> listJuge(Article article);
	@Transactional
	void deleteAll();
	void deleteById(Long id);
}
