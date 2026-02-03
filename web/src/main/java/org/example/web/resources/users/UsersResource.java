package org.example.web.resources.users;

import DTO.UsersDTO;
import DTO.UsersPatchDTO;
import interfaces.users.UsersServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.UsersModel;
import org.example.web.annotation.JwtRolesAllowed;
import users.ClientRoles;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    private UsersServiceInterface userService;

    public UsersResource() {
    }

    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})
    @POST
    public Response createUser(UsersDTO dto) {
        this.userService.createUser(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})
    @GET
    @Path("/{id}")
    public UsersModel getUser(@PathParam("id") Long id) {
        return this.userService.getUsersById(id);
    }


    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})
    @GET
    public List<UsersModel> getAllUser(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        return this.userService.getAllUsers(page, size);
    }


    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PATCH
    public Response updateUser(@PathParam("id") Long id, UsersPatchDTO dto) {
        this.userService.updateUsers(id,dto);
        return Response.ok().build();
    }

    @JwtRolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        this.userService.deleteUsersById(id);
        return Response.noContent().build();
    }

}