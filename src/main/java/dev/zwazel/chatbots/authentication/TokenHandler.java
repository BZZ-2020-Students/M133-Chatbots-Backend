package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.HelloApplication;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.exception.NotLoggedInException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Cookie;
import lombok.NonNull;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

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
     * @param subject   the subject of the token.
     * @param user      the user for which the token is generated.
     * @param ttlMillis the time until the token expires.
     * @return the generated token.
     * @author Zwazel
     * @since 0.1
     */
    public static String createJWT(String subject, User user, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = HelloApplication.getProperty("jwt.secret", HelloApplication.defaultConfJwtSecret).getBytes();

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
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
        return builder
                .claim("username", user.getUsername())
                .claim("role", user.getUserRole())
                .compact();
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

    /**
     * returns the role of the user owning the given token.
     *
     * @param jwt the token to decode.
     * @return the username of the user owning the token.
     * @author Zwazel
     * @since 1.4
     */
    public static UserRole getUserRole(String jwt) {
        String roleString = decodeJWT(jwt).get("role", String.class);

        return UserRole.valueOf(roleString.toUpperCase(Locale.ROOT));
    }

    /**
     * Gets a user from a token.
     *
     * @param jwtCookie the cookie with the jwt token.
     * @return the user owning the token.
     * @throws NotLoggedInException if the user is not logged in.
     * @author Zwazel
     * @since 1.4
     */
    public static User getUserFromJWT(Cookie jwtCookie) throws NotLoggedInException {
        if (jwtCookie == null) {
            throw new NotLoggedInException();
        }

        String jwt = jwtCookie.getValue();
        UserDao userDao = new UserDao();
        String username = getUsername(jwt);
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        return user;
    }

    /**
     * Gets a user by directly getting the cookie out of the request context.
     *
     * @param requestContext the request context.
     * @return the user owning the token.
     * @throws NotLoggedInException if the user is not logged in.
     * @author Zwazel
     * @since 1.4
     */
    public static User getUserFromCookie(@NonNull ContainerRequestContext requestContext) throws NotLoggedInException {
        Cookie jwtCookie = requestContext.getCookies().get(HelloApplication.getProperty("jwt.name"));
        return getUserFromJWT(jwtCookie);
    }
}
