package org.example.web.resources;

import DTO.UsersDTO;
import interfaces.UsersServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.UsersModel;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    private UsersServiceInterface userService;

    public UsersResource() {
    }

    @POST
    public Response createUser(UsersDTO dto) {
        userService.createUser(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public UsersModel getUser(@PathParam("id") Long id) {
        return userService.getUsersById(id);
    }

    @GET
    public List<UsersModel> getAllUser(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        return userService.getAllUsers(page, size);
    }

    @PUT
    public Response updateUser(UsersDTO dto) {
        userService.updateUsers(dto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUsersById(id);
        return Response.noContent().build();
    }

}
