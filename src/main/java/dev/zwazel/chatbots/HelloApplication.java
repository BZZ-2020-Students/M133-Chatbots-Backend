package dev.zwazel.chatbots;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
@DeclareRoles({"USER", "ADMIN"})
public class HelloApplication extends Application {

}