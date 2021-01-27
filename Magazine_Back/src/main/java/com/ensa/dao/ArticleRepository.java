package com.ensa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;

@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {
	Article getById(Long id);
	
	// recuperer les articles par son etat d'evaluation
	@Query("select a from Article a where a.etat like :x")
	public List<Article> getArticlesByEtat(@Param("x") String etat); 
	
	// pour un auteur correspondant d'un article, on recupere ses articles en attente d'evaluation
	@Query("select dep.article from Deposition dep where dep.status like 'Correspondant' and dep.article.etat like 'en attente' and dep.intervenant like :x")
	public List<Article> articleEnattenteByIntervenant(@Param("x") Intervenant inter);
	
	// pour un auteur correspondant d'un article, on recupere tous ses articles
	@Query("select dep.article from Deposition dep where dep.status like 'Correspondant' and dep.intervenant like :x order by dep.article.etat")
	public List<Article> articleByIntervenant(@Param("x") Intervenant inter);
	
	// pour modifier certains champs d'un article
	@Modifying
    @Query("UPDATE Article a SET a.affiliation = :aff, a.resume = :res, a.titre = :titre  WHERE a.id = :id")
    public int updateArticle(@Param("aff") String aff, @Param("res") String res, @Param("titre") String titre, @Param("id") Long id);
	
	// lorsque le comité prend une decision, on modifie la decision finale de l'article
	@Modifying
    @Query("UPDATE Article a SET a.decisionFinal = :decision, a.etat = :etat  WHERE a.id = :id")
    public int updateArticleDecision(@Param("decision") String decision, @Param("etat") String etat, @Param("id") Long id);

	@Query("select a from Article a where a.decisionFinal like :x")
	public List<Article> getArticlesByDecisionFinale(@Param("x") String decision); 
}
