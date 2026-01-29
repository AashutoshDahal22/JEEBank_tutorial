package org.example.web.resources;

import DTO.AccountsDTO;
import interfaces.AccountsServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.AccountsModel;
import java.util.List;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    private AccountsServiceInterface accountsService;

    public AccountResource() {
    }

    @POST
    public Response createAccounts(AccountsDTO dto) {
        accountsService.createAccount(dto);
        return Response.status(Response.Status.CREATED).build(); //returns the response builder object and build() method builds the response
    }

    @GET
    @Path("/{id}")
    public AccountsModel getAccountsById(@PathParam("id") Long userId) {
        return accountsService.getAccountsById(userId);
    }

    @GET
    public List<AccountsModel> getAllAccounts(@QueryParam("page") @DefaultValue("1") int page,@QueryParam("size") @DefaultValue("10") int size) {
        return accountsService.getAllAccounts(page ,size);
    }


    @DELETE
    @Path("/{userid}")
    public Response deleteAccounts(@PathParam("userid") Long userid) {
        accountsService.deleteAccountById(userid);
        return Response.noContent().build();
    }
}
