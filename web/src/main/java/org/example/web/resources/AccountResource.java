package org.example.web.resources;

import DTO.AccountsDTO;
import interfaces.AccountsServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.AccountsModel;
import org.example.web.annotation.JwtRolesAllowed;

import java.util.List;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    private AccountsServiceInterface accountsService;

    public AccountResource() {
    }

    @JwtRolesAllowed({"User","Admin"})
    @POST
    public Response createAccounts(AccountsDTO dto) {
        this.accountsService.createAccount(dto);
        return Response.status(Response.Status.CREATED).build(); //returns the response builder object and build() method builds the response
    }

    @JwtRolesAllowed({"User","Admin"})
    @GET
    @Path("/{id}")
    public AccountsModel getAccountsById(@PathParam("id") Long userId) {
        return this.accountsService.getAccountsById(userId);
    }

    @JwtRolesAllowed({"User","Admin"})
    @GET
    public List<AccountsModel> getAllAccounts(@QueryParam("page") @DefaultValue("1") int page,@QueryParam("size") @DefaultValue("10") int size) {
        return this.accountsService.getAllAccounts(page ,size);
    }

    @JwtRolesAllowed({"Admin"})
    @DELETE
    @Path("/{userid}")
    public Response deleteAccounts(@PathParam("userid") Long userid) {
        this.accountsService.deleteAccountById(userid);
        return Response.noContent().build();
    }
}
