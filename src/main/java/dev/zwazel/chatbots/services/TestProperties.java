package dev.zwazel.chatbots.services;

import dev.zwazel.chatbots.HelloApplication;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/properties")
public class TestProperties {
    @GET
    @Produces("text/plain")
    public String getProperties() {
        return HelloApplication.getProperty("jwtToken");
    }
}
