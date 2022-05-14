package dev.zwazel.chatbots.configs;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * Filter class for allowing authorization with CORS
 * <br>
 * refer to <a href="https://www.baeldung.com/cors-in-jax-rs#1-using-the-filter">Baeldung.com</a>
 *
 * @version 1.0
 * @since 0.1
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    /**
     * Sets the response headers to allow CORS
     *
     * @param requestContext  the request context
     * @param responseContext the response context
     * @throws IOException if there is an error
     * @since 0.1
     */
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin", "http://localhost:3000");
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