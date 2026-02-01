package org.example.web.resources.authentication;

import DTO.LoginRequestDTO;
import DTO.LoginResponseDTO;
import authentication.AuthenticationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
    @Inject
    private AuthenticationService authenticationService;

    @POST
    public Response login(LoginRequestDTO request) {
        try {
            // Call the service layer to handle login
            LoginResponseDTO response = authenticationService.login(request);
            return Response.ok(response).build();
        } catch (RuntimeException e) {
            // Return 401 Unauthorized if login fails
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}
