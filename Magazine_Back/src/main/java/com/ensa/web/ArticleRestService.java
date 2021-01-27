package com.ensa.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.ensa.dao.ArticleRepository;
import com.ensa.dao.MotCleRepository;
import com.ensa.entities.Affectation;
import com.ensa.entities.Article;
import com.ensa.entities.Deposition;
import com.ensa.entities.Intervenant;
import com.ensa.entities.MotCle;
import com.ensa.service.IAffectation;
import com.ensa.service.IArticle;
import com.ensa.service.IDeposition;


@RestController
@CrossOrigin("*")
public class ArticleRestService {
	@Autowired
	IArticle articleMetier;
	
	@Autowired
	IDeposition depoMetier;
	
	@Autowired
	MotCleRepository mcMetier;
	
	@Autowired
	IAffectation affMetier;
	
	
	// admin (comite) + auteur + non authentifie
	@GetMapping("/articles/search")
	public List<Article> getArticlesByKeywordAuteur(@RequestParam(value="keyword", required = false) String keyword, @RequestParam(value="auteur", required = false) String auteur){
		return articleMetier.getArticlesByKeywordAuteur(keyword, auteur);
	}
			
	// admin (comite) + auteur + non authentifie
	@GetMapping("/articles/publies")
	public List<Article> getArticlesPublies(){
		return articleMetier.getArticlesByDecisionFinale("accepté");
	}
			
	// admin (comite) + auteur
	@PostMapping("/articles")
	public Article saveArticle(@RequestBody Article obj) {
		
		obj.setEtat("en attente");
		obj.setDecisionFinal("en attente de traitement");
		
		return articleMetier.save(obj);

	}

	// admin (comite) + auteur
	@PostMapping("/articles/{id}/manuscrite")
	public BodyBuilder uplaodFile(@RequestParam("manuscriteFile") MultipartFile file, @PathVariable Long id) throws IOException {
		
		articleMetier.saveFile(file, id);
		return ResponseEntity.status(HttpStatus.OK);
	}
	
	// admin (comite) + auteur
	@PostMapping("/articles/{id}/motCle")
	public List<MotCle> saveArticleMotCle(@RequestBody List<String> listMotCle, @PathVariable Long id) {
	
		return articleMetier.saveArticleMotCle(listMotCle, id);

	}

	// admin (comite) + auteur
	@DeleteMapping("/articles/{id}")
	public void deleteArticle(@PathVariable Long id){
		articleMetier.deleteById(id);
	}
	
	// admin (comite) + auteur
	@PutMapping("/articles/{id}")
	public Article updateArticle(@PathVariable Long id,@RequestBody Article obj) throws ParseException{
		
		articleMetier.updateArticle(obj);
		
		return obj;
	}

	// admin (comite) + auteur
	@GetMapping("/articles/{id}/affectations")
	public List<Affectation> getAffectationsArticle(@PathVariable Long id){
		return affMetier.affectationByArticle(articleMetier.getById(id));
	}
	
	
	// admin (comite) + auteur
	@GetMapping("/articles/{id}/manuscrite")
	public Article downloadFile(@PathVariable Long id) throws IOException {
		
		Article article = articleMetier.getById(id);
		return article;
	}	

	
	// admin (comite)
	@PostMapping("/articles/{id}/affecter")
	public List<Affectation> saveAffectationJuge(@RequestBody List<Intervenant> listInter, @PathVariable Long id) throws ParseException {
				
		return affMetier.saveAffectationJuge(listInter, articleMetier.getById(id));

	}
	
	// admin (comite)
	@PostMapping("/articles/{id}/refuser")
	public void refuserArticle(@RequestBody Article article, @PathVariable Long id) throws ParseException {
		articleMetier.refuserArticle(article);
	}	
	
	// admin (comite)
	@GetMapping("/articles/enAttente")
	public List<Article> getArticlesEnAttente(){
		return articleMetier.getArticlesByEtat("en attente");
	}
	
	// admin (comite)
	@GetMapping("/articles/enCours")
	public List<Article> getArticlesEnCours(){
		return articleMetier.getArticlesByEtat("en cours");
	}
		
	// admin (comite)
	@PutMapping("/articles/{id}/decision")
	public Article updateArticleDecisionEtat(@PathVariable Long id,@RequestBody Article obj) throws ParseException{
		
		articleMetier.updateArticleDecisionEtat(obj);
		
		return obj;
	}

	
//	@GetMapping("/articles/{id}")
//	public Article getArticle(@PathVariable Long id){
//		return articleMetier.getById(id);
//	}
//	
//	@GetMapping("/articles")
//	public List<Article> getArticles(){
//		return articleMetier.getAll();
//	}
	
}

