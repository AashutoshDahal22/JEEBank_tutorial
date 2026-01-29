package org.example.web;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@ApplicationPath("/api")

//we should only have once application layer which will be our base url even though we can have multiple application
public class MainApplication extends Application {

}