package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.HelloApplication;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/auth")
public class AuthenticateResource {
    @POST
    @Path("/login")
    @PermitAll
    @Produces("application/json")
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);

        NewCookie tokenCookie = new NewCookie(
                HelloApplication.getProperty("jwt.name", "auth-jwt"),
                TokenHandler.createJWT(
                        "1",
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
    @PermitAll
    public String logout() {
        return "You're now logged out!";
    }

    @GET
    @PermitAll
    @Path("auth-check")
    public String authCheck() {
        return "idk if you're authenticated";
    }

    @GET
    @RolesAllowed("admin")
    @Path("/admin-check")
    public String adminCheck() {
        return "you're an admin";
    }

    @GET
    @RolesAllowed("user")
    @Path("/user-check")
    public String userCheck() {
        return "you're a user";
    }

    @GET
    @RolesAllowed({"admin", "user"})
    @Path("/any-check")
    public String anyRoleCheck() {
        return "you're an admin or a user";
    }
}
