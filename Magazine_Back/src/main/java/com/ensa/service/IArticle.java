package com.ensa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;
import com.ensa.entities.MotCle;

@Component
public interface IArticle {
	public Article save(Article objet);
	public Article getById(Long id);
	public List<Article> getAll();
	public List<Article> getArticlesByEtat(String etat);
	public List<Article> getArticlesByKeywordAuteur(String keyword, String auteur);
	public List<MotCle> saveArticleMotCle(List<String> listMotCle, Long id);
	public List<Article> articleEnattenteByIntervenant(Intervenant inter);
	public List<Article> articleByIntervenant(Intervenant inter);
	public void saveFile(MultipartFile file, Long id);
	
	@Transactional
	void deleteAll();
	void deleteById(Long id);
	public Article updateArticle(Article obj);
	public void refuserArticle(Article article);
	public void updateArticleDecisionEtat(Article obj);
	public List<Article> getArticlesByDecisionFinale(String string);
}
