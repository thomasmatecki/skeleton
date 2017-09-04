package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// For a Java class to be eligible to receive ANY requests
// it must be annotated with at least @Path
@Path("/netid")
@Produces(MediaType.APPLICATION_JSON)
public class NetidController {

  // You can specify additional @Path steps; they will be relative
  // to the @Path defined at the class level
  @GET
  public String netid() {
    return "tpm93";
  }
}
