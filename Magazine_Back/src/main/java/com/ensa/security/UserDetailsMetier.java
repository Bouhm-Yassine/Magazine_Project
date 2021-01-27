package com.ensa.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ensa.dao.UtilisateurRepository;
import com.ensa.entities.Utilisateur;
import com.ensa.service.IUtilisateur;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UserDetailsMetier implements UserDetailsService {

	@Autowired
	UtilisateurRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Utilisateur user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username + "n\'existe pas !!");
		}
		
		// les roles ou les autorisations de spring security c'est une collection de type GrantedAuthority
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
		user.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRole()));
		});
		
		// System.out.println(user.getIntervenant().getNom());
		// user.getCollab().getId().toString()
		return new User(user.getIntervenant().getId().toString() , user.getPassword(), authorities);
	}

}
