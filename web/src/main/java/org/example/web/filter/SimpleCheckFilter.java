package org.example.web.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;


@Provider
public class SimpleCheckFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Logging Incoming request: " + requestContext.getMethod() + " " + requestContext.getUriInfo().getPath());

        if ("DELETE".equals(requestContext.getMethod())) {
            requestContext.abortWith(Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("DELETE NOT ALLOWED").build());
        }
    }
}