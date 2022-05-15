package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.HelloApplication;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 *
 * @author Zwazel
 * @since 0.1
 */
public class TokenHandler {
    /**
     * Generates a JWT token for the given username and subject.
     *
     * @param id        the id of the token. todo: still unsure what this is.
     * @param subject   the subject of the token.
     * @param username  the username of the user owning this token.
     * @param ttlMillis the time until the token expires.
     * @return the generated token.
     * @author Zwazel
     * @since 0.1
     */
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

    /**
     * Decodes the given JWT token and returns the claims.
     * If the token is invalid/not signed, an exception is thrown.
     *
     * @param jwt the JWT token to decode.
     * @return the claims of the JWT token.
     * @author Zwazel
     * @since 0.1
     */
    public static Claims decodeJWT(String jwt) {
        byte[] apiKeySecretBytes = HelloApplication.getProperty("jwt.secret", HelloApplication.defaultConfJwtSecret).getBytes();

        //This line will throw an exception if it is not a signed JWS (as expected)
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(apiKeySecretBytes))
                .build()
                .parseClaimsJws(jwt).getBody();
    }

    /**
     * returns the username of the user owning the given token.
     *
     * @param jwt the token to decode.
     * @return the username of the user owning the token.
     * @author Zwazel
     * @since 0.1
     */
    public static String getUsername(String jwt) {
        return decodeJWT(jwt).get("username", String.class);
    }
}
