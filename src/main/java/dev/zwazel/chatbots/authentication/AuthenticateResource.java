package dev.zwazel.chatbots.authentication;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthenticateResource {
    @GET
    @Path("/login")
    @Produces("application/json")
    public Response login() {
        return Response.ok("{\"message\": \"You're now logged in!\"}").cookie(
                new NewCookie("auth", "true")).build();
    }

    @GET
    @Path("/logout")
    public String logout() {
        return "You're now logged out!";
    }

    @GET
    @Path("auth-check")
    public String authCheck() {
        return "idk if you're authenticated";
    }

    @GET
    @Path("/admin-check")
    public String loginCheck() {
        return "idk if you're an admin";
    }
}
