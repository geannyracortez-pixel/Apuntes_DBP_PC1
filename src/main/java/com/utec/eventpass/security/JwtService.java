package com.utec.eventpass.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Crea un token que identifica al usuario (por su email), con fecha de expiracion y firmado
    public String generateToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)                                      // identifica al usuario
                .issuedAt(now)                                       // fecha de creacion
                .expiration(new Date(now.getTime() + expiration))    // fecha de expiracion
                .signWith(getKey())                                  // firma
                .compact();
    }

    // Lee el email guardado dentro del token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // El token es valido si pertenece al usuario y no ha expirado
    public boolean isTokenValid(String token, String email) {
        Claims claims = getClaims(token);
        return claims.getSubject().equals(email) && claims.getExpiration().after(new Date());
    }

    // Verifica la firma y devuelve el contenido del token.
    // Si la firma es falsa o el token expiro, lanza una excepcion.
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
