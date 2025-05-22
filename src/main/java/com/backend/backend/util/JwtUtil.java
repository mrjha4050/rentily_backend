package com.backend.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + 10 * 60 * 1000);

    private final String SECRET_KEY = "BACKEND909009009LAUCNHMAY31313HELLOWORLD2323";

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256 , SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token, String userEmail){
        return extractEmail(token).equals(userEmail);
    }


}
