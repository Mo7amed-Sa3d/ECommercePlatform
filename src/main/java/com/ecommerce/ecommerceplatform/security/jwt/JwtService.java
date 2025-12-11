package com.ecommerce.ecommerceplatform.security.jwt;

import com.ecommerce.ecommerceplatform.dto.requestdto.AuthRequestDto;
import com.ecommerce.ecommerceplatform.dto.responsedto.AuthResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {


    @Value("${jwt.secret.key}")
    String SECRET_KEY;

    private final BlacklistService blacklistService;

    public JwtService( BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public void logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7); // remove Bearer
        blacklistService.blackListToken(token);
    }

    public String generateToken(String userEmail) {
        // 24 hours
        long EXPIRATION_TIME = 1000 * 60 * 60 * 24;
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String userEmail) {
        return userEmail.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
