package com.ensa.service;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ensa.entities.Affectation;
import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;


@Component
public interface IAffectation {
	
	public Affectation save(Affectation objet);
	public Affectation getById(Long id);
	public List<Affectation> getAll();
	public List<Affectation> affectationByArticle(Article article);
	public int updateAffectation(String decision, String commentaire, Long id);
	@Transactional
	void deleteAll();
	void deleteById(Long id);
	public List<Affectation> saveAffectationJuge(List<Intervenant> listInter, Article article) throws ParseException;
	public List<Affectation> affectationByIntervenant(Intervenant byId);

}
