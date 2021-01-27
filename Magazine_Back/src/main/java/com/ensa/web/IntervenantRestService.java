package com.ensa.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensa.entities.Affectation;
import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;
import com.ensa.service.IAffectation;
import com.ensa.service.IArticle;
import com.ensa.service.IIntervenant;

@RestController
@CrossOrigin("*")
public class IntervenantRestService {
	
	@Autowired
	IIntervenant intervenantMetier;
	
	@Autowired
	IArticle articleMetier;
	
	@Autowired
	IAffectation affMetier;
	
	// admin (comite) + auteur
	@GetMapping("/intervenants/{id}/articles")
	public List<Article> getArticleByIntervenant(@PathVariable Long id) {
		return articleMetier.articleByIntervenant(intervenantMetier.getById(id));
	}

	// admin (comite) + auteur
	@GetMapping("/intervenants/{id}/affectations")
	public List<Affectation> getAffectaionsByIntervenant(@PathVariable Long id) {
		return affMetier.affectationByIntervenant(intervenantMetier.getById(id));
	}
	
	// admin (comite) + auteur
	@GetMapping("/intervenants/auteurs")
	public List<Intervenant> getAuteurs(){
		return intervenantMetier.getAuteurs();
	}
	
	// admin (comite)
	@GetMapping("/intervenants/juges")
	public List<Intervenant> getJuges(@RequestParam("id") Long id){
		return intervenantMetier.listJuge(articleMetier.getById(id));
	}
	
//	@PostMapping("/intervenants")
//	public Intervenant saveIntervenant(@RequestBody Intervenant obj) {
//		
//		return intervenantMetier.save(obj);
//
//	}
//	
//	@GetMapping("/intervenants")
//	public List<Intervenant> getIntervenants(){
//		return intervenantMetier.getAll();
//	}
//	
//	@DeleteMapping("/intervenants/{id}")
//	public void deleteIntervenant(@PathVariable Long id){
//		intervenantMetier.deleteById(id);
//	}
//	
//	@PutMapping("/intervenants/{id}")
//	public Intervenant updateIntervenant(@PathVariable Long id,@RequestBody Intervenant obj){
//		
//		obj.setId(id);
//		return intervenantMetier.save(obj);
//	}
//
//	@GetMapping("/intervenants/{id}")
//	public Intervenant getIntervenant(@PathVariable Long id){
//		return intervenantMetier.getById(id);
//	}
//	

}
