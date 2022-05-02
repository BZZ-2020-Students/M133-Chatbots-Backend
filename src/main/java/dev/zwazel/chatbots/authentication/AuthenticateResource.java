package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.classes.enums.UserRole;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.SecurityContext;

@Path("/auth")
public class AuthenticateResource {
    @Inject
    private SecurityContext securityContext;

    @GET
    @Path("/login")
    public String login() {
        return "You're now logged in!";
    }

    @GET
    @Path("/logout")
    public String logout() {
        return "You're now logged out!";
    }

    @GET
    @Path("auth-check")
    public String authCheck() {
        return "You're authenticated!";
    }

    @GET
    @Path("/admin-check")
    public String loginCheck() {
        if (securityContext.isUserInRole(UserRole.ADMIN.toString())) {
            return "You're an Admin!";
        } else {
            return "You're not an Admin!";
        }
    }
}
