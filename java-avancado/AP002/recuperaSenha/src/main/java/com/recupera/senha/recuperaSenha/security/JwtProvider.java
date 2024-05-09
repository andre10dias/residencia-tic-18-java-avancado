package com.recupera.senha.recuperaSenha.security;

import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.recupera.senha.recuperaSenha.model.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

	private final JwtEncoder jwtEncoder;
	
	public static final Long EXPIRATION_TIME_IN_SECONDS = 3600L;
	
	
	public String generateToken(Authentication authentication) {
		log.info("Authentication generateToken: {}", authentication.toString());
		User principal = (User) authentication.getPrincipal();
		log.info("Principal: {}", principal.toString());
		var now = Instant.now();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(EXPIRATION_TIME_IN_SECONDS))
				.subject(principal.getUsername())
				.claim("scope", "ROLE_USER")
				.build();
		
		return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
				
	}
	
	public String generateToken(Usuario user) {
		var now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(EXPIRATION_TIME_IN_SECONDS))
				.subject(user.getUsername())
				.claim("scope", "ROLE_USER")
				.build();
		return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
}
