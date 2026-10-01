package com.ecommerce.auth_service.config;

import com.ecommerce.auth_service.dto.LoginRequestBody;
import com.ecommerce.auth_service.dto.UserResponseBody;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String secert = "5674qa12ed98sxcsafxcXa5s5s8dxs59c3z2s4d8v8ggr6";

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secert);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String generateToken(LoginRequestBody payLoad) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, payLoad);
    }

    public String createToken(Map<String, Object> claims, LoginRequestBody payLoad) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(payLoad.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 mins
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
