package com.ensa.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

// va s executer pour chaque requete
public class JWTAuthorisationFilter extends OncePerRequestFilter{
	
	// pour verifier est ce que le token envoyer est valid ou pas ! 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		// les entetes qu on autorise
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
	
		// ppur autoriser l utilisation des entetes par l app du front end
		// on les expose
	 	response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
	 	response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, UPDATE");
		
        
	 	String token = request.getHeader(SecurityConstants.HEADER_STRING);
	 	
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			// si le cas s une req avec method option c a d on veut just interoger le serveur.
			// il repond avec les entetes ce dessus
		
		} else {
			
			if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;// pour des filtre secondaire	
			}
			
			// on analyse le token et en suite on trim le prefix pour avoir just le corps du token.
			Claims claims = Jwts.parser()
					.setSigningKey(SecurityConstants.SECRET)
					.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
					.getBody();
			
			String username = claims.getSubject();
			
			ArrayList<Map<String, String>> roles =  (ArrayList<Map<String, String>>) claims.get("roles"); 
			
			Collection<GrantedAuthority> authorities = new ArrayList<>();
		
			roles.forEach(r -> {
				authorities.add(new SimpleGrantedAuthority(r.get("authority")));
			});
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
			
			// pour que spring connaitre user qui a envoyé la requete 
			SecurityContextHolder.getContext().setAuthentication(authToken);
			
			filterChain.doFilter(request, response);
		}
		
	}
	
	

}
