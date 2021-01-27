package com.ensa.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ensa.dao.ArticleRepository;
import com.ensa.dao.MotCleRepository;
import com.ensa.entities.Article;
import com.ensa.entities.Deposition;
import com.ensa.entities.Intervenant;
import com.ensa.entities.MotCle;
import com.ensa.service.IArticle;


@Service
public class ArticleService implements IArticle {

	@Autowired
	ArticleRepository articleRepo;
	
	@Autowired
	MotCleRepository mcRepository;
	
	private static final Logger logger = Logger.getLogger(ArticleService.class.getName());
	
	@Override
	public void saveFile(MultipartFile file, Long id) {
		Article article = new Article();
		try {
			article = articleRepo.getById(id);
			article.setManuscrite(file.getBytes());
			articleRepo.save(article);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Ajoutant le manuscrite pour un article");
	}
	
	@Override
	public Article save(Article objet) {
		// TODO Auto-generated method stub
		return articleRepo.save(objet);
	}

	@Override
	public Article getById(Long id) {
		// TODO Auto-generated method stub
		return articleRepo.getById(id);
	}

	@Override
	public List<Article> getAll() {
		// TODO Auto-generated method stub
		return articleRepo.findAll();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		articleRepo.deleteAll();

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		logger.info("Suprimant un article");
		articleRepo.deleteById(id);

	}

	@Override
	public List<Article> getArticlesByEtat(String etat) {
		// TODO Auto-generated method stub
		return articleRepo.getArticlesByEtat(etat);
	}

	// pour faire la recherche ou bien le filtrage || On a 4 cas:
	@Override
	public List<Article> getArticlesByKeywordAuteur(String keyword, String auteur) {
		List<Article> listArticleFiltred = new ArrayList<>();
		List<Article> listArticle = getArticlesByDecisionFinale("accepté");
		
		// 1ere cas
		if(keyword.equals("") && auteur.equals("")) {
			listArticleFiltred = listArticle;
			logger.info("La recherche des articles publies");
		}
		
		// 2eme cas: on cherche avec l'auteur et le mot cle
		if(!keyword.equals("") && !auteur.equals("")) {
			List<Article> list = new ArrayList<>();
			for(Article a : listArticle) {
				for(MotCle mot: a.getMotsCles()) {
					if(mot.getLibelle().equals(keyword)) {
						list.add(a);
						break;
					}
					else {
						continue;
					}
				}
			}
			
			if(list.size() != 0) {
				for(Article a : list) {
					for(Deposition depos : a.getDepositions()) {
						if(depos.getIntervenant().getNom().equals(auteur)) {
							listArticleFiltred.add(a);
							break;
						}
						else {
							continue;
						}
					}
				}
			}

			logger.info("La recherche des articles publies avec un mot cle et un auteur");
		}
		
		// 3 eme cas: on cherche avec le mot cle
		if(!keyword.equals("") && auteur.equals("")) {
			for(Article a : listArticle) {
				for(MotCle mot: a.getMotsCles()) {
					if(mot.getLibelle().equals(keyword)) {
						listArticleFiltred.add(a);
						break;
					}
					else {
						continue;
					}
				}
			}

			logger.info("La recherche des articles publies avec un mot cle");
		}
		
		// 4 eme cas: on cherche avec l'auteur
		if(!auteur.equals("") && keyword.equals("")) {
			for(Article a : listArticle) {
				for(Deposition depos : a.getDepositions()) {
					if(depos.getIntervenant().getNom().equals(auteur)) {
						listArticleFiltred.add(a);
						break;
					}
					else {
						continue;
					}
				}
			}

			logger.info("La recherche des articles publies avec un auteur");
		}
		
		return listArticleFiltred;
	}

	@Override
	public List<MotCle> saveArticleMotCle(List<String> listMotCle, Long id) {
		List<MotCle> list = new ArrayList<>();	
		Article article = getById(id);
		
		for(String mot : listMotCle) {
			MotCle mc = new MotCle();
			mc.setLibelle(mot);
			mc.setArticle(article);
			mcRepository.save(mc);
			list.add(mc);
		}

		logger.info("Ajoutant des mot cles pour un article");
		
		return list;
	}

	@Override
	public List<Article> articleEnattenteByIntervenant(Intervenant inter) {
		// TODO Auto-generated method stub
		return articleRepo.articleEnattenteByIntervenant(inter);
	}

	@Override
	public List<Article> articleByIntervenant(Intervenant inter) {
		// TODO Auto-generated method stub
		return articleRepo.articleByIntervenant(inter);
	}

	@Override
	public Article updateArticle(Article obj) {
		articleRepo.updateArticle(obj.getAffiliation(), obj.getResume(), obj.getTitre(), obj.getId());
		
		logger.info("La modification de Affiliation, Resume, Titre pour un article");
		return obj;
	}

	@Override
	public void refuserArticle(Article article) {
		articleRepo.updateArticleDecision("traité", "refus - ne concerne pas la revue", article.getId());
		logger.info("Le refus de l'article par le comité car ne concerne pas la revue");
	}

	@Override
	public void updateArticleDecisionEtat(Article obj) {
		articleRepo.updateArticleDecision(obj.getDecisionFinal(), obj.getEtat(), obj.getId());
		logger.info("La décision finale par le comité pour l'article: "+obj.getTitre());
	}

	@Override
	public List<Article> getArticlesByDecisionFinale(String decision) {
		// TODO Auto-generated method stub
		return articleRepo.getArticlesByDecisionFinale(decision);
	}

}
