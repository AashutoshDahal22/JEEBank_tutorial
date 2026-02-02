package org.example.web.resources.accounts;

import DTO.AccountStatusUpdateDTO;
import DTO.AccountsDTO;
import accounts.AccountsStatus;
import exception.InvalidDataException;
import interfaces.accounts.AccountsServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.AccountsModel;
import models.UsersModel;
import org.example.web.annotation.JwtRolesAllowed;
import users.ClientRoles;

import java.util.List;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    private AccountsServiceInterface accountsService;

    public AccountResource() {
    }

    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})
    @POST
    public Response createAccounts(AccountsDTO dto) {
        this.accountsService.createAccount(dto);
        return Response.status(Response.Status.CREATED).build(); //returns the response builder object and build() method builds the response
    }

    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})
    @GET
    @Path("/{id}")
    public AccountsModel getAccountsById(@PathParam("id") Long userId) {
        return this.accountsService.getAccountsById(userId);
    }

    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})
    @GET
    public List<AccountsModel> getAllAccounts(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        return this.accountsService.getAllAccounts(page, size);
    }

    @JwtRolesAllowed({ClientRoles.ADMIN})
    @DELETE
    @Path("/{userid}")
    public Response deleteAccounts(@PathParam("userid") Long userid) {
        this.accountsService.deleteAccountById(userid);
        return Response.noContent().build();
    }

    @JwtRolesAllowed({ClientRoles.ADMIN})
    @PATCH
    @Path("/{accountNumber}/status/{userId}")
    public Response updateAccountStatus(@PathParam("accountNumber") Long accountNumber, AccountStatusUpdateDTO dto, @PathParam("userId")Long userId) {
        AccountsModel user = accountsService.getAccountsById(userId);
        if (user.getStatus().equals(AccountsStatus.FROZEN) && dto.getStatus().equals(AccountsStatus.FROZEN))
            throw new InvalidDataException("Cannot freeze already frozen account");
        accountsService.updateAccountStatus(accountNumber, dto);
        return Response.ok().entity("Account status updated").build();
    }

}
