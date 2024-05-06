package com.bhulai.waran.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	private static final String SERCET = "128A0062F52ED9A72A72C4639C140C59E63D5ACB0C22CA82D334CA56944AB06EB859A2BD684034827CA4EF23617D85DAA8EED1E930348DE0D96C6C6656A06D9FFD3FD07686F2A81A67D291BDE91A7E3D64315A6FE4F0B1EAD6C9DB3AE3C64AA6D8F70110F862825459C3FC7BBDCCF449CFDADA099DC44ABB6C7C3E5F3EC30971";
	
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}
	
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	 public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUserName(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(getSignKey())
				   .build()
				   .parseClaimsJws(token)
				   .getBody();
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SERCET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
