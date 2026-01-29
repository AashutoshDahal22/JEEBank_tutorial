package org.example.web.resources;

import interfaces.TransactionsServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.TransactionsModel;

@Path("/transaction")
public class TransactionalResource {

    @Inject
    private TransactionsServiceInterface transactionsService;

    public TransactionalResource(){}

    @POST
    @Path("/transfer")
    public Response transfer(TransactionsModel transactions) {
        transactionsService.transfer(transactions);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/checkBalance/{userId}")
    public Response checkBalance(@PathParam("userId") Long userId) {
        Double balance = transactionsService.checkBalance(userId);
        return Response.ok(balance).build();
    }

    @POST
    @Path("/deposit")
    public Response deposit(TransactionsModel transactions) {
        transactionsService.deposit(transactions);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/withdraw")
    public Response withdraw(TransactionsModel transactions) {
        transactionsService.withdraw(transactions);
        return Response.status(Response.Status.CREATED).build();
    }
}
