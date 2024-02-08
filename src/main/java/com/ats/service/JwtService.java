package com.ats.service;

import com.ats.util.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.validity.refresh.token}") // LONG
    public long JWT_REFRESH_TOKEN_VALIDITY;
    @Value("${jwt.validity.access.token}") // SHORT
    public long JWT_ACCESS_TOKEN_VALIDITY;
    @Value("${jwt.secret.refresh.token}")
    private String refreshTokenSecret;
    @Value("${jwt.secret.access.token}")
    private String accessTokenSecret;

    // To store logged out tokens
    private final Set<String> invalidatedTokens = new HashSet<>();

    //retrieve username from jwt token
    public String getUsernameFromToken(String token, TokenType tokenType) {
        return getClaimFromToken(token, Claims::getSubject, tokenType);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token, TokenType tokenType) {
        return getClaimFromToken(token, Claims::getExpiration, tokenType);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, TokenType tokenType) {
        final Claims claims = getAllClaimsFromToken(token, tokenType);
        return claimsResolver.apply(claims);
    }

    // for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token, TokenType tokenType) {
        if(tokenType.equals(TokenType.ACCESS)){
            return Jwts
                    .parserBuilder()
                    .setSigningKey(accessTokenSecret.toString())
                    .build()
                    .parseClaimsJws(token).getBody();
        }
        else {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(refreshTokenSecret.toString())
                    .build()
                    .parseClaimsJws(token).getBody();
        }
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token, TokenType tokenType) {
        final Date expiration = getExpirationDateFromToken(token, tokenType);
        return expiration.before(new Date());
    }

    // generate access token for user
    public String generateAccessToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), JWT_ACCESS_TOKEN_VALIDITY, accessTokenSecret);
    }

    // generate refresh token for user
    public String generateRefreshToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), JWT_REFRESH_TOKEN_VALIDITY, refreshTokenSecret);
    }

    // while creating the token -
    // 1. Define  claims of the token, like IsUser, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject, long validity, String tokenSecret) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity * 1000))
                .signWith(SignatureAlgorithm.HS512, tokenSecret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails, TokenType tokenType) {
        final String username = getUsernameFromToken(token, tokenType);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, tokenType));
    }

    // When user logs out blacklisting the token
    public void blacklistToken(String token) {
        invalidatedTokens.add(token);
    }

    public boolean isTokenInBlacklist(String token) {
        return !invalidatedTokens.contains(token);
    }
}