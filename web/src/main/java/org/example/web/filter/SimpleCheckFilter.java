package org.example.web.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import utils.JwtUtil;

import java.io.IOException;


@Provider
public class SimpleCheckFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Incoming request: " + requestContext.getMethod() + " " + requestContext.getUriInfo().getPath());
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

        String token = authHeader.substring("Bearer ".length());
        DecodedJWT decoded = JwtUtil.validateToken(token);
        String role = decoded.getClaim("role").asString();

        if ("DELETE".equals(requestContext.getMethod()) && !"ADMIN".equals(role)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Admin role required").build());
        }
    }
}