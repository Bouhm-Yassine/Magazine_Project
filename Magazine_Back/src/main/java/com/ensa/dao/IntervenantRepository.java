package com.ensa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensa.entities.Article;
import com.ensa.entities.Intervenant;

@Repository
@Transactional
public interface IntervenantRepository extends JpaRepository<Intervenant, Long> {
	Intervenant getById(Long id);
	
	// on recupere une list des auteurs acceptés à juger des articles
	// il faut que l'auteur ne fait pas partie des co auteir de l'article
	@Query("select inter from Intervenant inter where inter not in (select depo.intervenant from Deposition depo where depo.article like :x)")
	public List<Intervenant> listJuge(@Param("x") Article article);

}
