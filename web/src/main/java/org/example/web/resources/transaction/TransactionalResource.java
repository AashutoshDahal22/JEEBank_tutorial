package org.example.web.resources.transaction;

import interfaces.transactions.TransactionsServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.TransactionsModel;
import org.example.web.annotation.JwtRolesAllowed;
import users.ClientRoles;

@Path("/transaction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionalResource {

    @Inject
    private TransactionsServiceInterface transactionsService;

    public TransactionalResource(){}


    @JwtRolesAllowed({ClientRoles.USER})
    @POST
    @Path("/transfer")
    public Response transfer(TransactionsModel transactions) {
        this.transactionsService.transfer(transactions);
        return Response.status(Response.Status.CREATED).build();
    }

    @JwtRolesAllowed({ClientRoles.USER, ClientRoles.ADMIN})

    @GET
    @Path("/checkBalance/{userId}")
    public Response checkBalance(@PathParam("userId") Long userId) {
        Double balance = this.transactionsService.checkBalance(userId);
        return Response.ok(balance).build();
    }

    @JwtRolesAllowed({ClientRoles.USER})

    @POST
    @Path("/deposit")
    public Response deposit(TransactionsModel transactions) {
        this.transactionsService.deposit(transactions);
        return Response.status(Response.Status.CREATED).build();
    }

    @JwtRolesAllowed({ClientRoles.USER})
    @POST
    @Path("/withdraw")
    public Response withdraw(TransactionsModel transactions) {
        this.transactionsService.withdraw(transactions);
        return Response.status(Response.Status.CREATED).build();
    }
}
