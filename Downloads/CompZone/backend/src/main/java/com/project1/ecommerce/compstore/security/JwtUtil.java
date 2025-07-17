package com.project1.ecommerce.compstore.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.repositories.UsersRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
    private Key getSigningKey() {
    	byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    	return Keys.hmacShaKeyFor(keyBytes);
    }
    
    private final UsersRepository usersRepository;  // ✅ Marked as final and assigned via constructor

   
    public JwtUtil(UsersRepository usersRepository) {  // ✅ Constructor injection
        this.usersRepository = usersRepository;
        System.out.println("✅ usersRepository is null? " + (this.usersRepository == null));
    }
    

    public String generateToken(String username) {
        Users user = usersRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId()); // 👈 this line adds userId to token payload

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(String.valueOf(user.getEmail()))
            .setIssuer("my-app")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractUsername(String token) {
    	 if (token == null || token.trim().isEmpty()) {
    	        throw new IllegalArgumentException("Token is missing");
    	    }
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username);
    }
}
