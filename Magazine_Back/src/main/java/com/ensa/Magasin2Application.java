package com.ensa;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ensa.dao.MotCleRepository;
import com.ensa.entities.Article;
import com.ensa.entities.Deposition;
import com.ensa.entities.Intervenant;
import com.ensa.entities.MotCle;
import com.ensa.entities.Role;
import com.ensa.entities.Utilisateur;
import com.ensa.service.IArticle;
import com.ensa.service.IDeposition;
import com.ensa.service.IIntervenant;
import com.ensa.service.IRole;
import com.ensa.service.IUtilisateur;


@SpringBootApplication
public class Magasin2Application {
	
	@Bean 
	// cad le resulatat returner par l execution de cette methode devient un bean spring
	// du cout on peut l injecter par tous.
	public BCryptPasswordEncoder getBCrypt() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) throws ParseException {
		// SpringApplication.run(Magasin2Application.class, args);
		
		ApplicationContext ctx =  SpringApplication.run(Magasin2Application.class, args);
		
		IArticle articleRepo =(IArticle) ctx.getBean(IArticle.class);
		IUtilisateur userRepo =(IUtilisateur) ctx.getBean(IUtilisateur.class);
		IRole roleRepo =(IRole) ctx.getBean(IRole.class);
		IIntervenant intervenantRepo =(IIntervenant) ctx.getBean(IIntervenant.class);
		IDeposition depoRepo =(IDeposition) ctx.getBean(IDeposition.class);
		MotCleRepository motRepo =(MotCleRepository) ctx.getBean(MotCleRepository.class);
		
		roleRepo.save(new Role("ADMIN"));
		roleRepo.save(new Role("AUTEUR"));
		
		Article a = new Article();
		a.setTitre("Article 1");
		a.setDecisionFinal("accepté");
		a.setEtat("traité");
		a.setResume("Mon resume Mon resume Mon resume Mon resume ");
		articleRepo.save(a);
		
		MotCle m1 = new MotCle();
		m1.setLibelle("mot 1");
		m1.setArticle(a);
		motRepo.save(m1);
		
		MotCle m2 = new MotCle();
		m2.setLibelle("mot 2");
		m2.setArticle(a);
		motRepo.save(m2);
		
		Intervenant i1 = new Intervenant();
		i1.setNom("auteur 1");
		intervenantRepo.save(i1);
		
		Intervenant i2 = new Intervenant();
		i2.setNom("auteur 2");
		intervenantRepo.save(i2);
		
		Deposition d1 = new Deposition();
		d1.setArticle(a);
		d1.setIntervenant(i1);
		d1.setStatus("Correspondant");
		depoRepo.save(d1);
		
		Deposition d2 = new Deposition();
		d2.setArticle(a);
		d2.setIntervenant(i2);
		d2.setStatus("Co-Auteur");
		depoRepo.save(d2);
		
		//
		
		Article a1 = new Article();
		a1.setTitre("Article 2");
		a1.setDecisionFinal("accepté");
		a1.setEtat("traité");
		a1.setResume("Mon resume Mon resume Mon resume Mon resume ");
		articleRepo.save(a1);
		
		MotCle m3 = new MotCle();
		m3.setLibelle("mot 3");
		m3.setArticle(a1);
		motRepo.save(m3);
		
		MotCle m4 = new MotCle();
		m4.setLibelle("mot 4");
		m4.setArticle(a1);
		motRepo.save(m4);
		
		Intervenant i3 = new Intervenant();
		i3.setNom("auteur 3");
		intervenantRepo.save(i3);
		
		Intervenant i4 = new Intervenant();
		i4.setNom("auteur 4");
		intervenantRepo.save(i4);
		
		Deposition d3 = new Deposition();
		d3.setArticle(a1);
		d3.setIntervenant(i3);
		d3.setStatus("Correspondant");
		depoRepo.save(d3);
		
		Deposition d4 = new Deposition();
		d4.setArticle(a1);
		d4.setIntervenant(i4);
		d4.setStatus("Co-Auteur");
		depoRepo.save(d4);
		
		//
		
		Utilisateur u1 = new Utilisateur();
		u1.setUsername("admin");
		u1.setPassword("admin");
		u1.setIntervenant(i1);
		userRepo.save(u1);
		userRepo.addRoleToUser("admin", "ADMIN");
		
		Utilisateur u2 = new Utilisateur();
		u2.setUsername("a2");
		u2.setPassword("a2");
		u2.setIntervenant(i2);
		userRepo.save(u2);
		userRepo.addRoleToUser("a2", "AUTEUR");
		
		Utilisateur u3 = new Utilisateur();
		u3.setUsername("a3");
		u3.setPassword("a3");
		u3.setIntervenant(i3);
		userRepo.save(u3);
		userRepo.addRoleToUser("a3", "AUTEUR");
		
		Utilisateur u4 = new Utilisateur();
		u4.setUsername("a4");
		u4.setPassword("a4");
		u4.setIntervenant(i4);
		userRepo.save(u4);
		userRepo.addRoleToUser("a4", "AUTEUR");

	}

}
