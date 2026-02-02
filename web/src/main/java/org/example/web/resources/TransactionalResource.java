package org.example.web.resources;

import interfaces.TransactionsServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.TransactionsModel;
import org.example.web.annotation.JwtRolesAllowed;

@Path("/transaction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionalResource {

    @Inject
    private TransactionsServiceInterface transactionsService;

    public TransactionalResource(){}

    @JwtRolesAllowed({"User"})
    @POST
    @Path("/transfer")
    public Response transfer(TransactionsModel transactions) {
        this.transactionsService.transfer(transactions);
        return Response.status(Response.Status.CREATED).build();
    }

    @JwtRolesAllowed({"User","Admin"})
    @GET
    @Path("/checkBalance/{userId}")
    public Response checkBalance(@PathParam("userId") Long userId) {
        Double balance = this.transactionsService.checkBalance(userId);
        return Response.ok(balance).build();
    }

    @JwtRolesAllowed({"User"})
    @POST
    @Path("/deposit")
    public Response deposit(TransactionsModel transactions) {
        this.transactionsService.deposit(transactions);
        return Response.status(Response.Status.CREATED).build();
    }

    @JwtRolesAllowed({"User"})
    @POST
    @Path("/withdraw")
    public Response withdraw(TransactionsModel transactions) {
        this.transactionsService.withdraw(transactions);
        return Response.status(Response.Status.CREATED).build();
    }
}
