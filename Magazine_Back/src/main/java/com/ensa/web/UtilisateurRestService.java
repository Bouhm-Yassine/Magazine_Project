package com.ensa.web;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensa.entities.Intervenant;
import com.ensa.entities.RegisterForm;
import com.ensa.entities.Utilisateur;
import com.ensa.service.IUtilisateur;

@RestController
public class UtilisateurRestService {
	
	@Autowired
	IUtilisateur utilisateurMetier;
	
	@PostMapping("/register")
	public Utilisateur saveUtilisateur(@RequestBody RegisterForm obj) throws ParseException {
		if(!obj.getPassword().equals(obj.getRepassword()))
			throw new RuntimeException("Merci de confirmer votre mot de pass");
		
		Utilisateur user = utilisateurMetier.findUtilisateurByUsername(obj.getUsername());
		if(user != null)
			throw new RuntimeException("Le nom d'utilisateur exist déja");
		
		return utilisateurMetier.createAccount(obj);

	}
	
//	@GetMapping("/utilisateurs")
//	public List<Utilisateur> getUtilisateurs(@PathVariable Long id){
//		return utilisateurMetier.getAll();
//	}
//	
//	@DeleteMapping("/utilisateurs/{id}")
//	public void deleteUtilisateur(@PathVariable Long id){
//		utilisateurMetier.deleteById(id);
//	}
//	
//	@PutMapping("/utilisateurs/{id}")
//	public Utilisateur updateUtilisateur(@PathVariable Long id,@RequestBody Utilisateur obj){
//		
//		obj.setId(id);
//		return utilisateurMetier.save(obj);
//	}
//
//	@GetMapping("/utilisateurs/{id}")
//	public Utilisateur getUtilisateur(@PathVariable Long id){
//		return utilisateurMetier.getById(id);
//	}
	

}
