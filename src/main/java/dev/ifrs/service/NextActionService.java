package dev.ifrs.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dev.ifrs.NextAction;
import dev.ifrs.dao.NextActionDAOImpl;

@Path("/nextAction")
public class NextActionService {

    @Inject
    NextActionDAOImpl dao;

    @GET
    @Path("listTasks/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NextAction> listInboxTasks(@PathParam("userId") final String userId) {
        return dao.listInboxTasks(userId);
    }
}