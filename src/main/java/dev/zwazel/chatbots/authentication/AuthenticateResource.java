package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.HelloApplication;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

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
        System.out.println("username = " + username);
        System.out.println("password = " + password);

        NewCookie tokenCookie = new NewCookie(
                HelloApplication.getProperty("jwt.name", HelloApplication.defaultConfJwtName),
                TokenHandler.createJWT(
                        "1", // TODO: 10.05.2022 - change to user id
                        "authentication",
                        username,
                        DurationsInMilliseconds.DAY.getDuration()
                ),
                "/",
                "",
                "Auth-Token",
                (int) (DurationsInMilliseconds.DAY.getDuration() / 1000),
                false
        );

        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .cookie(tokenCookie)
                .build();
    }

    /**
     * Logs out a user. At the moment, this is a dummy method.
     * todo: implement
     *
     * @return a response with a status code of 200
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("/logout")
    public String logout() {
        return "You're now logged out!";
    }

    /**
     * Checks if a user is logged in and what role he has. Right now, this is a dummy method.
     * todo: implement
     *
     * @return a response with the needed information about the user and a status code depending on the result
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("auth-check")
    @Produces("application/json")
    public Response authCheck() {
        User user = User.builder()
                .username("Zwazel")
                .password("1234")
                .userRole(UserRole.USER)
                .build();

        return Response
                .status(200)
                .entity(user.toJson())
                .build();
    }

    /**
     * Utility method for testing purposes. Tests if the user is logged in and has admin rights.
     * todo: implement
     *
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("/admin-check")
    public String adminCheck() {
        return "you're an admin";
    }

    /**
     * Utility method for testing purposes. Tests if the user is logged in and has normal user rights.
     * todo: implement
     *
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("/user-check")
    public String userCheck() {
        return "you're a user";
    }
}
