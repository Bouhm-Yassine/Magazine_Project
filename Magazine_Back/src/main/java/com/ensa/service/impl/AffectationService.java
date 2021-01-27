package com.ensa.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensa.dao.AffectationRepository;
import com.ensa.dao.ArticleRepository;
import com.ensa.entities.Affectation;
import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;
import com.ensa.service.IAffectation;

@Service
public class AffectationService implements IAffectation {
	
	@Autowired
	AffectationRepository affRepo;
	
	@Autowired
	ArticleRepository articleRepo;
	
	private static final Logger logger = Logger.getLogger(AffectationService.class.getName());


	@Override
	public Affectation save(Affectation objet) {
		// TODO Auto-generated method stub
		return affRepo.save(objet);
	}

	@Override
	public Affectation getById(Long id) {
		// TODO Auto-generated method stub
		return affRepo.getById(id);
	}

	@Override
	public List<Affectation> getAll() {
		// TODO Auto-generated method stub
		return affRepo.findAll();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		affRepo.deleteAll();

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		affRepo.deleteById(id);

	}

	// pour adresser un article à des juges pour l'evaluer
	@Override
	public List<Affectation> saveAffectationJuge(List<Intervenant> listInter, Article article) throws ParseException {
		List<Affectation> list = new ArrayList<>();
		
		for(Intervenant juge : listInter) {
			Affectation aff = new Affectation();
			aff.setArticle(article);
			aff.setIntervenant(juge);
			affRepo.save(aff);
			list.add(aff);
		}
		
		article.setEtat("en cours");
		article.setDecisionFinal("en cours d'evaluation");
		articleRepo.save(article);
		
		logger.info("Adressant un article à des juges");
		
		return list;
	}

	@Override
	public List<Affectation> affectationByIntervenant(Intervenant inter) {
		// TODO Auto-generated method stub
		return affRepo.affectationByIntervenant(inter);
	}

	@Override
	public List<Affectation> affectationByArticle(Article article) {
		
		return affRepo.affectationByArticle(article);
	}

	@Override
	public int updateAffectation(String decision, String commentaire, Long id) {
		// TODO Auto-generated method stub
		logger.info("Evaluation d'un article:");
		return affRepo.updateAffectation(decision, commentaire, id);
	}

}
