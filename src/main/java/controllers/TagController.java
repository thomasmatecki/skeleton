package controllers;

import api.AddTagRequest;
import dao.TagDao;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {

  final TagDao tags;

  public TagController(TagDao tags) {
    this.tags = tags;
  }

  @POST
  public int createTag(AddTagRequest tag) {
    return 2;
  }
}
