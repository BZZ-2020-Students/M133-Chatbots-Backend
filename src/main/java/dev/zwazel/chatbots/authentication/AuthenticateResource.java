package dev.zwazel.chatbots.authentication;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/auth")
public class AuthenticateResource {
    @GET
    @Path("/login")
    @PermitAll
    @Produces("application/json")
    public Response login() {
        return Response.ok("{\"message\": \"You're now logged in!\"}").cookie(
                new NewCookie("auth", "true")).build();
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
