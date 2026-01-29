package org.example.web.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/home")
public class MainResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "This is the home of the api";
    }
}