package org.example.web.resources.authentication;

import DTO.UsersDTO;
import authentication.AuthenticationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    @Inject
    private AuthenticationService authenticationService;

    public RegisterResource() {

    }

    @POST
    public Response register(UsersDTO dto) {
        authenticationService.register(dto);
        return Response.status(Response.Status.CREATED).build();
    }


}
