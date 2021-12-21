package dev.ifrs.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dev.ifrs.NextActionState;
import dev.ifrs.dao.NextActionDAOImpl;

@Path("/nextAction")
public class NextActionService {

    @Inject
    private NextActionDAOImpl dao;

    @GET
    @Path("listInbox/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, NextActionState> listInbox(@PathParam("userId") final String userId) {
        return dao.listInboxTasks(userId);
    }
}