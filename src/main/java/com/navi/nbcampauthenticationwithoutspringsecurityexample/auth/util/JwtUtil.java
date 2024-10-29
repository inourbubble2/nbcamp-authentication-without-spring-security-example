package com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.util;

import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.access-token-secret-key}")
    private String accessTokenSecretKey;

    @Value("${jwt.access-token-expires-in}")
    private long accessTokenExpiresIn;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] decodedAccessTokenSecretKey = Base64.getDecoder().decode(accessTokenSecretKey);
        key = Keys.hmacShaKeyFor(decodedAccessTokenSecretKey);
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
            .setSubject(user.getId().toString())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiresIn))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractSubject(String accessToken) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
        } catch (ExpiredJwtException e) {
            throw new IllegalStateException("token is expired");
        } catch (JwtException e) {
            throw new IllegalStateException("token is not valid");
        }
    }
}
