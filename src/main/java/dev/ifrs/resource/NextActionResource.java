package dev.ifrs.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.ifrs.dao.NextActionDAOImpl;
import dev.ifrs.model.NextAction;

@Path("/nextAction")
public class NextActionResource {

  @Inject
  NextActionDAOImpl dao;

  @GET
  @Path("list/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<NextAction> list(@PathParam("userId") final String userId) {
    return dao.listTasks(userId);
  }

  @POST
  @Path("add/{userId}/{type}/{title}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response add(@PathParam("userId") final String userId,
                      @PathParam("type") final int type,
                      @PathParam("title") final String title) {
    dao.addTask(userId, type, title);
    return Response.ok().build();
  }

  @POST
  @Path("complete/{userId}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response complete(@PathParam("userId") final String userId,
                           @PathParam("taskId") final String taskId) {
    dao.completeTask(userId, taskId);
    return Response.ok().build();
  }

  @POST
  @Path("rename/{userId}/{taskId}/{newTitle}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response complete(@PathParam("userId") final String userId,
                           @PathParam("taskId") final String taskId,
                           @PathParam("newTitle") final String newTitle) {
    dao.renameTask(userId, taskId, newTitle);
    return Response.ok().build();
  }

  @POST
  @Path("context/{userId}/{taskId}/{newContext}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("userId") final String userId,
                         @PathParam("taskId") final String taskId,
                         @PathParam("newContext") final String newContext) {
    dao.updateContext(userId, taskId, newContext);
    return Response.ok().build();
  }

  @POST
  @Path("delete/{userId}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("userId") final String userId,
                         @PathParam("taskId") final String taskId) {
    dao.deleteTask(userId, taskId);
    return Response.ok().build();
  }
}