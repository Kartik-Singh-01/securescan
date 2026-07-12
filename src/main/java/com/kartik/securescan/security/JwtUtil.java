package com.kartik.securescan.security;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "MySecureScanSuperSecretKeyForJwtAuthentication2026";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // Generate JWT Token
    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();

    }

    // Extract Username
    public String extractUsername(String token) {

        return getClaims(token).getSubject();

    }

    // Validate Token
    public boolean validateToken(String token, String username) {

        return extractUsername(token).equals(username)
                && !isTokenExpired(token);

    }

    // Check Expiry
    private boolean isTokenExpired(String token) {

        return getClaims(token)
                .getExpiration()
                .before(new Date());

    }

    // Read Claims
    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

}