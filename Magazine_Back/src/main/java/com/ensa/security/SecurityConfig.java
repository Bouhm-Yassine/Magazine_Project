package com.ensa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.HttpMediaTypeException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// il faut creer des impl de linterface UserDetails
	// et d'instancier BCrypt.
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// pour montrer a spring security comment va chercher les user et roles
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// pour definir les droits d accees
		// on va travailler avec JWT odnc faut desactiver le Synchronizer Token
		http.csrf().disable();
		
		// pour desactiver auth basé sur les session 
		// donc on informe spring d'utiliser auth avec le mode statless
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
		// http.formLogin();
		
		http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
		http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/articles/search", "/articles/publies").permitAll();
		
		// Article
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/articles/{id}/affecter", "/articles/{id}/refuser").hasAuthority("ADMIN");		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/articles/enCours", "/articles/enAttente").hasAuthority("ADMIN");		
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/articles/{id}/decision").hasAuthority("ADMIN");		
	
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/articles", "/articles/{id}/manuscrite", "/articles/{id}/motCle").hasAnyAuthority("ADMIN", "AUTEUR");		
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/articles/{id}").hasAnyAuthority("ADMIN", "AUTEUR");		
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/articles/{id}").hasAnyAuthority("ADMIN", "AUTEUR");		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/articles/{id}/affectations", "/articles/{id}/manuscrite").hasAnyAuthority("ADMIN", "AUTEUR");		
					
		// Affectations
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/affectations").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/affectations/{id}").hasAnyAuthority("ADMIN", "AUTEUR");
		
		// Deposition
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/depositions").hasAnyAuthority("ADMIN", "AUTEUR");
		
		// Intervenant
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/intervenants/juges").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/intervenants/auteurs", "/intervenants/{id}/affectations", "/intervenants/{id}/articles").hasAnyAuthority("ADMIN", "AUTEUR");
		
		
		http.authorizeRequests().anyRequest().authenticated();
		
		// authenticationManager() une fct du spring qui va retourner l object principale d auth de spring security
 		http.addFilter(new JWTAuthFilter(authenticationManager()));
 		
 		// before un filter qui est basé sur username et mot de pass
 		http.addFilterBefore(new JWTAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
}
