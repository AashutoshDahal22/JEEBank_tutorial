package org.example.web.filter;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.example.web.annotation.JwtRolesAllowed;
import utils.JwtUtil;

import java.io.IOException;
import java.util.Arrays;


@Provider
@Priority(Priorities.AUTHENTICATION)
public class SimpleCheckFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (path.endsWith("login") || path.endsWith("register")) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Missing or invalid token").build());
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();
        String role;
        try {
            role = JwtUtil.extractRole(token);
            System.out.println("Role " + role);

            if (role == null) {
                throw new Exception("Invalid token");
            }
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid token").build());
            return;
        }

        // Get method-level annotation
        JwtRolesAllowed rolesAnnotation = this.resourceInfo.getResourceMethod().getAnnotation(JwtRolesAllowed.class);
        if (rolesAnnotation == null) {
            rolesAnnotation = this.resourceInfo.getResourceClass().getAnnotation(JwtRolesAllowed.class);
        }

        if (rolesAnnotation != null) {
            System.out.println("Roles allowed by annotation: " + Arrays.toString(rolesAnnotation.value()));

            boolean allowed = Arrays.stream(rolesAnnotation.value())
                    .anyMatch(r -> r.equals(role));

            if (!allowed) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Forbidden: insufficient role").build());
                return;
            }
        }
    }
}