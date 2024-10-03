package com.emc.belustyle.util;

import com.emc.belustyle.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {


    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    private SecretKey SECRET_KEY;

    public JwtUtil() {
        String secretKey = "f1ef94ed31b03d27bd4eb8b43127c96103726fcad9b5e04fcb434b87af4728b1";
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateUserToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("googleId", user.getGoogleId())
                .claim("role", user.getAuthorities())
                .claim("fullName", user.getFullName())
                .claim("email", user.getEmail())
                .claim("image", user.getUserImage())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody());
    }


    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }


}
