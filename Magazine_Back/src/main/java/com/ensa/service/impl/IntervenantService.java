package com.ensa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensa.dao.IntervenantRepository;
import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;
import com.ensa.entities.Role;
import com.ensa.service.IIntervenant;


@Service
public class IntervenantService implements IIntervenant {
	
	@Autowired
	IntervenantRepository interRepo;
	
	private static final Logger logger = Logger.getLogger(IntervenantService.class.getName());
	
	@Override
	public Intervenant save(Intervenant objet) {
		// TODO Auto-generated method stub
		return interRepo.save(objet);
	}

	@Override
	public Intervenant getById(Long id) {
		// TODO Auto-generated method stub
		return interRepo.getById(id);
	}

	@Override
	public List<Intervenant> getAll() {
		// TODO Auto-generated method stub
		return interRepo.findAll();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		interRepo.deleteAll();

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		interRepo.deleteById(id);

	}

	@Override
	public List<Intervenant> getAuteurs() {
		List<Intervenant> listAuteurs = new ArrayList<>();
		
		for(Intervenant inter : getAll()) {
			for(Role role : inter.getUtilisateur().getRoles()) {
				if(role.getRole().equals("AUTEUR")) {
					listAuteurs.add(inter);
					break;
				}
				else {
					continue;
				}
			}
		}
			
		logger.info("Recuperant une list des auteurs");
		return listAuteurs;
	}

	@Override
	public List<Intervenant> listJuge(Article article) {
		List<Intervenant> listJuges = new ArrayList<>();
		
		for(Intervenant inter : interRepo.listJuge(article)) {
			for(Role role : inter.getUtilisateur().getRoles()) {
				if(role.getRole().equals("AUTEUR")) {
					listJuges.add(inter);
					break;
				}
				else {
					continue;
				}
			}
		}
		
		logger.info("Recuperant une list des juges");		
		return listJuges;
	}

}
