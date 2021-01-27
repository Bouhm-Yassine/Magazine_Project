package com.ensa.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensa.dao.IntervenantRepository;
import com.ensa.dao.RoleRepository;
import com.ensa.dao.UtilisateurRepository;
import com.ensa.entities.Utilisateur;
import com.ensa.entities.Intervenant;
import com.ensa.entities.RegisterForm;
import com.ensa.entities.Role;
import com.ensa.service.IUtilisateur;



@Service
@Transactional
public class UtilisateurService implements IUtilisateur {

	@Autowired
	IntervenantRepository interRepo;
	
	@Autowired
	UtilisateurRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Utilisateur save(Utilisateur objet) {
		String pwCrypted = passwordEncoder.encode(objet.getPassword());
		objet.setPassword(pwCrypted);
		return userRepo.save(objet);
	}

	@Override
	public Utilisateur getById(Long id) {
		// TODO Auto-generated method stub
		return userRepo.getById(id);
	}

	@Override
	public List<Utilisateur> getAll() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}
	
	@Override
	public void addRoleToUser(String username, String role) {
		Role obj = roleRepo.findByRole(role);
		Utilisateur user = userRepo.findByUsername(username);
		
		user.getRoles().add(obj);
	}

	@Override
	public Utilisateur findUtilisateurByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		userRepo.deleteAll();
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		userRepo.deleteById(id);

	}

	@Override
	public Utilisateur createAccount(RegisterForm obj) throws ParseException {
		Intervenant inter = new Intervenant();
		inter.setEmail(obj.getEmail());
		inter.setNom(obj.getNom());
		interRepo.save(inter);
		
		Utilisateur user = new Utilisateur();
		user.setUsername(obj.getUsername());
		user.setPassword(obj.getPassword());
		user.setIntervenant(inter);
		save(user);
		
		addRoleToUser(user.getUsername(), "AUTEUR");
		
		return user;
	}

}
