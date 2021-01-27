package com.ensa.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensa.dao.ArticleRepository;
import com.ensa.dao.DepositionRepository;
import com.ensa.dao.IntervenantRepository;
import com.ensa.entities.Article;
import com.ensa.entities.Deposition;
import com.ensa.entities.Intervenant;
import com.ensa.service.IDeposition;

@Service
public class DepositionService implements IDeposition {

	@Autowired
	DepositionRepository deposRepo;
	
	@Autowired
	IntervenantRepository interRepo;
	
	@Autowired
	ArticleRepository articleRepo;
	
	private static final Logger logger = Logger.getLogger(DepositionService.class.getName());
	
	@Override
	public List<Deposition> saveDeposition(List<Intervenant> coAuteur, Long id, Long idArticle) throws ParseException {
		List<Deposition> list = new ArrayList<>();
		
		Intervenant correspondant = interRepo.getById(id);
		Article article = articleRepo.getById(idArticle);
		
		Deposition depos = new Deposition();
		depos.setArticle(article);
		depos.setIntervenant(correspondant);
		depos.setStatus("Correspondant");
		deposRepo.save(depos);
		list.add(depos);
		
		for(Intervenant intervenant : coAuteur) {
			Deposition d = new Deposition();
			d.setArticle(article);
			d.setIntervenant(intervenant);
			d.setStatus("Co-Auteur");
			deposRepo.save(d);
			list.add(d);
		}
		
		logger.info("Enregistrant les co auteurs et le correspondant d'un article");
		
		return list;
	}

	@Override
	public Deposition getById(Long id) {
		// TODO Auto-generated method stub
		return deposRepo.getById(id);
	}

	@Override
	public List<Deposition> getAll() {
		// TODO Auto-generated method stub
		return deposRepo.findAll();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		deposRepo.deleteAll();
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		deposRepo.deleteById(id);
	}

	@Override
	public Deposition save(Deposition obj) {
		return deposRepo.save(obj);
	}

}
