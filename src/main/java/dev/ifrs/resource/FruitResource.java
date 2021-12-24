package dev.ifrs.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import dev.ifrs.model.Fruit;
import dev.ifrs.service.FruitSyncService;

@Path("/fruits")
public class FruitResource {

  @Inject
  FruitSyncService service;

  @GET
  public List<Fruit> getAll() {
    return service.findAll();
  }

  @GET
  @Path("{name}")
  public Fruit getSingle(@PathParam("name") String name) {
    return service.get(name);
  }

  @POST
  public List<Fruit> add(Fruit fruit) {
    service.add(fruit);
    return getAll();
  }
}