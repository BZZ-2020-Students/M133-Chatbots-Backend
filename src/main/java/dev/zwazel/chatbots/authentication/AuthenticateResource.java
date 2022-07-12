package dev.zwazel.chatbots.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.zwazel.chatbots.HelloApplication;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.exception.NotLoggedInException;
import dev.zwazel.chatbots.service.UserResource;
import dev.zwazel.chatbots.util.SHA256;
import dev.zwazel.chatbots.util.json.ToJson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import static dev.zwazel.chatbots.service.UserResource.getFilterProvider;

/**
 * Resource for authenticating users.
 *
 * @author Zwazel
 * @since 0.1
 */
@Path("/auth")
public class AuthenticateResource {
    /**
     * Logins a user.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return a response with a cookie if the login was successful, otherwise a response with a status code of 401
     * @author Zwazel
     * @since 0.1
     */
    @POST
    @Path("/login")
    @Produces("application/json")
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        UserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);
        if (user == null) {
            return Response
                    .status(404)
                    .entity("User with username not found")
                    .build();
        }

        if (!user.getPassword().equals(SHA256.getHexStringInstant(password))) {
            return Response
                    .status(401)
                    .entity("Wrong password")
                    .build();
        }

        DurationsInMilliseconds ttl = DurationsInMilliseconds.DAY;
        NewCookie tokenCookie = new NewCookie(
                HelloApplication.getProperty("jwt.name", HelloApplication.defaultConfJwtName),
                TokenHandler.createJWT(
                        user,
                        ttl.getDuration()
                ),
                "/",
                "",
                "Auth-Token",
                (int) (ttl.getDuration() / 1000),
                false
        );

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(user, UserResource.getFilterProvider(true)))
                    .cookie(tokenCookie)
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(500)
                    .entity("Could not serialize user")
                    .build();
        }
    }

    /**
     * Logs out a user.
     *
     * @return a response with a status code of 200
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("/logout")
    public Response logout() {
        NewCookie tokenCookie = new NewCookie(
                HelloApplication.getProperty("jwt.name", HelloApplication.defaultConfJwtName),
                "",
                "/",
                "",
                "Auth-Token",
                0,
                false
        );

        return Response
                .status(200)
                .entity("")
                .cookie(tokenCookie)
                .build();
    }

    /**
     * Checks if a user is logged in and what role he has.
     *
     * @param requestContext the request context, used to get the cookie with the token to get the user that is logged in
     * @return a response with the needed information about the user and a status code depending on the result
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("auth-check")
    @Produces("application/json")
    public Response authCheck(ContainerRequestContext requestContext) {
        Cookie cookie = requestContext.getCookies().get(HelloApplication.getProperty("jwt.name", HelloApplication.defaultConfJwtName));
        User user;
        try {
            user = TokenHandler.getUserFromJWT(cookie);
        } catch (NotLoggedInException e) {
            return Response
                    .status(201)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(user, getFilterProvider(true)))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Utility method for testing purposes. Tests if the user is logged in and has admin rights.
     *
     * @param requestContext the request context, used to get the cookie with the token to get the user that is logged in
     * @return a string explaining if the user is logged in and has admin rights or not
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Produces("text/plain")
    @Path("/admin-check")
    public String adminCheck(ContainerRequestContext requestContext) {
        Cookie cookie = requestContext.getCookies().get(HelloApplication.getProperty("jwt.name", HelloApplication.defaultConfJwtName));
        User user;
        try {
            user = TokenHandler.getUserFromJWT(cookie);
        } catch (NotLoggedInException e) {
            return e.getMessage();
        }

        if (UserRole.ADMIN == user.getUserRole()) {
            return "you're an admin";
        } else {
            return "you're not an admin";
        }
    }

    /**
     * Utility method for testing purposes. Tests if the user is logged in and has normal user rights.
     *
     * @param requestContext the request context, used to get the cookie with the token to get the user that is logged in
     * @return a string explaining if the user is logged in and has normal user rights or not
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("/user-check")
    public String userCheck(ContainerRequestContext requestContext) {
        Cookie cookie = requestContext.getCookies().get(HelloApplication.getProperty("jwt.name", HelloApplication.defaultConfJwtName));
        User user;
        try {
            user = TokenHandler.getUserFromJWT(cookie);
        } catch (NotLoggedInException e) {
            return e.getMessage();
        }

        if (UserRole.USER == user.getUserRole()) {
            return "you're a user";
        } else {
            return "you're not a user";
        }
    }
}
