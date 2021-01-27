package com.ensa.web;

import java.text.ParseException;
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

import com.ensa.entities.Deposition;
import com.ensa.entities.Intervenant;
import com.ensa.service.IDeposition;

@RestController
@CrossOrigin("*")
public class DepositionRestService {
	@Autowired
	IDeposition deposMetier;
	
	// admin (comite) + auteur
	@PostMapping("/depositions")
	public List<Deposition> saveDeposition(@RequestBody List<Intervenant> coAuteur, @RequestParam("idCorrespondant") Long id, @RequestParam("idArticle") Long idArticle ) throws ParseException{
		return deposMetier.saveDeposition(coAuteur, id, idArticle);
	}
	
//	@GetMapping("/depositions")
//	public List<Deposition> getDepositions(){
//		return deposMetier.getAll();
//	}
//	
//	@DeleteMapping("/depositions/{id}")
//	public void deleteDeposition(@PathVariable Long id){
//		deposMetier.deleteById(id);
//	}
//	
//	@PutMapping("/depositions/{id}")
//	public Deposition updateDeposition(@PathVariable Long id,@RequestBody Deposition obj){
//		obj.setId(id);
//		return deposMetier.save(obj);
//	}
//
//	@GetMapping("/depositions/{id}")
//	public Deposition getDeposition(@PathVariable Long id){
//		return deposMetier.getById(id);
//	}
	
}
