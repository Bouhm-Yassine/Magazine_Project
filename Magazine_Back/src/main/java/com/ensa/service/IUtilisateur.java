package com.ensa.service;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ensa.entities.RegisterForm;
import com.ensa.entities.Utilisateur;

@Component
public interface IUtilisateur {

	public Utilisateur save(Utilisateur objet);
	public Utilisateur getById(Long id);
	public void addRoleToUser(String username, String libelleRole);
	public Utilisateur findUtilisateurByUsername(String username);
	public List<Utilisateur> getAll();
	public Utilisateur createAccount(RegisterForm obj) throws ParseException;
	@Transactional
	void deleteAll();
	void deleteById(Long id);
}
