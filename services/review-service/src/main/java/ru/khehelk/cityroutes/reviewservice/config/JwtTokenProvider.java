package ru.khehelk.cityroutes.reviewservice.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.khehelk.cityroutes.reviewservice.config.properties.JwtProperties;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(
        UserDetails userDetails
    ) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
        Map<String, Object> claims,
        UserDetails userDetails
    ) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpirationInMs());

        return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date())
            .expiration(expiryDate)
            .signWith(getJwtSecretKey())
            .compact();
    }

    public Claims getClaimsFromJWT(String token) {
        return Jwts.parser()
            .verifyWith((SecretKey) getJwtSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getClaimsFromJWT(token).getSubject();
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaimsFromJWT(token).getExpiration().before(new Date());
    }

    private Key getJwtSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretBase64());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
