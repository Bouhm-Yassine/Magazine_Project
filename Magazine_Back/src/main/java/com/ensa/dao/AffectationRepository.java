package com.ensa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensa.entities.Affectation;
import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;
import com.ensa.entities.Utilisateur;

@Repository
@Transactional
public interface AffectationRepository extends JpaRepository<Affectation, Long> {
	Affectation getById(Long id);
	
	// pour recuperer les affectations d'un auteurs ( affectation c'est à dire les articles à juger)
	@Query("select aff from Affectation aff where aff.intervenant like :x order by aff.dateAffectation")
	public List<Affectation> affectationByIntervenant(@Param("x") Intervenant inter);
	
	// pour recuperer les evaluations des juges pour un article
	@Query("select aff from Affectation aff where aff.article like :x order by aff.dateAffectation")
	public List<Affectation> affectationByArticle(@Param("x") Article article);
	
	// evaluer un article en modifiant certains champs de l'affectation
	@Modifying
    @Query("UPDATE Affectation a SET a.decision = :decision, a.commentaire = :commentaire WHERE a.id = :id")
    public int updateAffectation(@Param("decision") String decision, @Param("commentaire") String commentaire, @Param("id") Long id);
	

}
