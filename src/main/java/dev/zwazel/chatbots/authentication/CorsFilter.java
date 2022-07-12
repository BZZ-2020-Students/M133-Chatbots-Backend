package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.HelloApplication;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

/**
 * Filter class for allowing authorization with CORS
 * <br>
 * refer to <a href="https://www.baeldung.com/cors-in-jax-rs#1-using-the-filter">Baeldung.com</a>
 *
 * @since 0.1
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    /**
     * Sets the response headers to allow CORS
     *
     * @param requestContext  the request context
     * @param responseContext the response context
     * @author Zwazel
     * @since 0.1
     */
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin", HelloApplication.getProperty("cors.allowed.origins", HelloApplication.defaultCorsAllowedOrigins));
        responseContext.getHeaders().add(
                "Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}