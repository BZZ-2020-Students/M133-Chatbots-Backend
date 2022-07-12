package dev.zwazel.chatbots.authentication;

import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.exception.NotLoggedInException;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter for authentication.
 *
 * @author Zwazel
 * @see <a href="https://github.com/bzz-fgict/M133-BookUltimate/blob/master/src/main/java/ch/bzz/bookultimate/util/AuthorizationFilter.java">AuthorizationFilter</a>
 * @since 1.4
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {
    /**
     * The context of the request.
     *
     * @since 1.4
     */
    @Context
    ResourceInfo resourceInfo;

    /**
     * filter method.
     *
     * @param requestContext request context.
     * @author Zwazel
     * @see <a href="https://github.com/bzz-fgict/M133-BookUltimate/blob/669b038e53740836a1d1e3dd9642ded9c12a244d/src/main/java/ch/bzz/bookultimate/util/AuthorizationFilter.java#L31">filter</a>
     * @since 1.4
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Access blocked for all users !!").build());
        } else if (!method.isAnnotationPresent(PermitAll.class) &&
                method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> requiredRoles = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

            User user = null;
            try {
                user = TokenHandler.getUserFromCookie(requestContext);
            } catch (NotLoggedInException e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity(e.getMessage()).build());
            }

            if (user == null || !isUserAllowed(requiredRoles, user.getUserRole().toString())) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
            }
        }
    }

    /**
     * Checks if the user is allowed to access the resource by checking if he has the needed roles.
     *
     * @param requiredRoles the required roles.
     * @param userRole      the user role.
     * @return true if the user is allowed to access the resource, false otherwise.
     * @author Zwazel
     * @see <a href="https://github.com/bzz-fgict/M133-BookUltimate/blob/669b038e53740836a1d1e3dd9642ded9c12a244d/src/main/java/ch/bzz/bookultimate/util/AuthorizationFilter.java#L59">isUserAllowed</a>
     * @since 1.4
     */
    private boolean isUserAllowed(final Set<String> requiredRoles, String userRole) {
        return requiredRoles.stream().anyMatch(role -> role.equalsIgnoreCase(userRole));
    }
}
