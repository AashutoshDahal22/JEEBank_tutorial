package org.example.web.resources;

import DTO.UsersDTO;
import interfaces.UsersServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.UsersModel;
import org.example.web.annotation.JwtRolesAllowed;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    private UsersServiceInterface userService;

    public UsersResource() {
    }

    @JwtRolesAllowed({"User","Admin"})
    @POST
    public Response createUser(UsersDTO dto) {
        this.userService.createUser(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @JwtRolesAllowed({"User","Admin"})
    @GET
    @Path("/{id}")
    public UsersModel getUser(@PathParam("id") Long id) {
        return this.userService.getUsersById(id);
    }

    @JwtRolesAllowed({"User","Admin"})
    @GET
    public List<UsersModel> getAllUser(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        return this.userService.getAllUsers(page, size);
    }

    @JwtRolesAllowed({"User","Admin"})
    @PUT
    public Response updateUser(UsersDTO dto) {
        this.userService.updateUsers(dto);
        return Response.ok().build();
    }

    @JwtRolesAllowed({"Admin"})
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        this.userService.deleteUsersById(id);
        return Response.noContent().build();
    }

}