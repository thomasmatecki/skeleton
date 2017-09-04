package controllers;

import api.ReceiptResponse;
import dao.TagDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {

  final TagDao tagDao;

  public TagController(TagDao tagDao) {
    this.tagDao = tagDao;
  }

  @GET
  @Path("/{tag}")
  public List<ReceiptResponse> allReceipts(@PathParam("tag") String tagName) {

    return tagDao.getAllReceiptsForTag(tagName)
        .stream()
        .map(ReceiptResponse::new)
        .collect(toList());

  }

  @PUT
  @Path("/{tag}")
  public void toggleTag(@PathParam("tag") String tagName, int receiptId) {

    if (tagDao.exists(receiptId, tagName)) {
      tagDao.delete(receiptId, tagName);
    } else {
      tagDao.insert(receiptId, tagName);
    }

  }

}
