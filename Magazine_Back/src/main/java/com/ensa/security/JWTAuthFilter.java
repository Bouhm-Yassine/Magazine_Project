package com.ensa.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ensa.dao.UtilisateurRepository;
import com.ensa.entities.Utilisateur;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	
	@Autowired
	UtilisateurRepository userRepository;	
	
	public JWTAuthFilter(AuthenticationManager authManager) {
		super();
		this.authManager = authManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Utilisateur user = null;
		
		try {
			// pour deserialiser le contenu json de requete en un objet utilisateur
			user = new ObjectMapper().readValue(request.getInputStream(), Utilisateur.class);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println("Hna --1");
		
		return authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),  user.getPassword()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
        		
		// on recupere le user principale (UserDetails) generer par la methode loadUserByUsername
		User springUser = (User) authResult.getPrincipal();
		
		// on genere mainteneant le token 
		// on places ces info dans le Payload du token
		String token = Jwts.builder()
				 // .setSubject(springUser.getUsername()) 
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPERATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.claim("roles", springUser.getAuthorities())
				.claim("intervenantId", springUser.getUsername())
				.compact();
		

		System.out.println("Hna --2");
		
		// on met dans la case authorisation du header notre Token genere avec un prefix
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
	}
	
}
