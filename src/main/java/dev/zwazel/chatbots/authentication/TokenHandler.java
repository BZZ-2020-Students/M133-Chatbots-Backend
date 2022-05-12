package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.HelloApplication;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class TokenHandler {
    public static String createJWT(String id, String subject, String username, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = HelloApplication.getProperty("jwt.secret", HelloApplication.defaultConfJwtSecret).getBytes();

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(HelloApplication.getProperty("jwt.issuer", HelloApplication.defaultConfJwtIssuer))
                .signWith(Keys.hmacShaKeyFor(apiKeySecretBytes), signatureAlgorithm);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + DurationsInMilliseconds.HOUR.getDuration();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.claim("username", username).compact();
    }

    public static Claims decodeJWT(String jwt) {
        byte[] apiKeySecretBytes = HelloApplication.getProperty("jwt.secret", HelloApplication.defaultConfJwtSecret).getBytes();

        //This line will throw an exception if it is not a signed JWS (as expected)
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(apiKeySecretBytes))
                .build()
                .parseClaimsJws(jwt).getBody();
    }

    public static String getUsername(String jwt) {
        return decodeJWT(jwt).get("username", String.class);
    }
}
