package com.emc.belustyle.security;

import com.emc.belustyle.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {


    private String SECRET_KEY = "f1ef94ed31b03d27bd4eb8b43127c96103726fcad9b5e04fcb434b87af4728b1";

    private Key getKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateTokenWithResetToken(String resetToken) {
        return Jwts.builder()
                .claim("resetToken", resetToken)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                .signWith(getKey())
                .compact();
    }

    public String generateUserToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().getRoleName())
                .claim("enable", user.getEnable())
                .claim("user_image",user.getUserImage())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getKey())
                .compact();
    }

    public String extractResetToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().get("resetToken", String.class);
    }

}
