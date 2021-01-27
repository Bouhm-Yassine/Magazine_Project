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
import org.springframework.web.bind.annotation.RestController;

import com.ensa.entities.Affectation;
import com.ensa.service.IAffectation;

@RestController
@CrossOrigin("*")
public class AffectationRestService {

	@Autowired
	IAffectation affMetier;
	
	// comite
	@PostMapping("/affectations")
	public Affectation saveAffectation(@RequestBody Affectation obj) {
		return affMetier.save(obj);
	}
	
	// admin (comite) + auteur
	@PutMapping("/affectations/{id}")
	public Affectation updateAffectation(@PathVariable Long id,@RequestBody Affectation obj){
		affMetier.updateAffectation(obj.getDecision(), obj.getCommentaire(), id);
		
		return obj;
	}	
	
	
//	@GetMapping("/affectations")
//	public List<Affectation> getAffectations(){
//		return affMetier.getAll();
//	}
//	
//	
//	@DeleteMapping("/affectations/{id}")
//	public void deleteAffectation(@PathVariable Long id){
//		affMetier.deleteById(id);
//	}
//	
//	
//	@GetMapping("/affectations/{id}")
//	public Affectation getAffectation(@PathVariable Long id){
//		return affMetier.getById(id);
//	}
	
}
