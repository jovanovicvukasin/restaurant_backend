package com.vukasin.restaurant.security;

import com.vukasin.restaurant.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    static final String AUDIENCE = "restaurantUser";

    //secretKey => key object
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities());
        claims.put("id", user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setAudience(AUDIENCE)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        String username;

        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issuedAt;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            issuedAt = claims.getIssuedAt();

        } catch (Exception e) {
            issuedAt = null;
        }
        return issuedAt;
    }

    public String refreshToken(String token) {
        String refreshedToken = null;

        try {
            final Claims claims = this.getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }

            claims.setIssuedAt(new Date());

            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(getSigningKey(), SignatureAlgorithm.ES256)
                    .compact();


        } catch (Exception e) {

        }

        return refreshedToken;
    }

    public Long getExpirationDate(String token) {
        try{
            final Claims claims = this.getClaimsFromToken(token);
            if(claims != null && claims.getExpiration() != null){
                Date expiration = claims.getExpiration();
                return (expiration.getTime() - System.currentTimeMillis()) / 1000;
            }

        } catch (Exception e) {
            return 0L;
        }
        return 0L;
    }

    public boolean isTokenExpired(String token) {
        Date expiration = null;

        try {
            final Claims claims = this.getClaimsFromToken(token);
            if (claims != null) {
                expiration = claims.getExpiration();
            }
        } catch (Exception e) {
            return true;
        }
        return expiration == null || expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    public  String getToken(jakarta.servlet.http.HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getAuthHeaderFromHeader(jakarta.servlet.http.HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


}
