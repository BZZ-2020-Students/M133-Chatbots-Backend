package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.HelloApplication;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthenticateResource {
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

    @GET
    @Path("/logout")
    public String logout() {
        return "You're now logged out!";
    }

    // checks if logged in / authenticated
    @GET
    @Path("auth-check")
    @Produces("application/json")
    public Response authCheck(HttpServletRequest request) {


        User user = new User("69", "Zwazel");

        return Response
                .status(200)
                .entity(user.toJson())
                .build();
    }

    @GET
    @Path("/admin-check")
    public String adminCheck() {
        return "you're an admin";
    }

    @GET
    @Path("/user-check")
    public String userCheck() {
        return "you're a user";
    }

    @GET
    @Path("/any-check")
    public String anyRoleCheck() {
        return "you're an admin or a user";
    }
}
