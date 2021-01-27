package com.ensa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensa.entities.Utilisateur;

@Repository
@Transactional
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	Utilisateur getById(Long id);
	public Utilisateur findByUsername(String username);

}
