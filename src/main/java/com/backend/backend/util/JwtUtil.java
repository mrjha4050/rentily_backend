package com.backend.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "BACKEND909009009LAUCNHMAY31313HELLOWORLD2323";
    private final long EXPIRATION_TIME_IN_MS = 24 * 60 * 60 * 1000; // 24 hours

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_IN_MS);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String userEmail) {
        try {
            String extractedEmail = extractEmail(token);
            Date expiration = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return extractedEmail.equals(userEmail) && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
