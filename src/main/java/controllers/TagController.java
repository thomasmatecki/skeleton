package controllers;

import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {

  final TagDao tagDao;
  final ReceiptDao receipts;

  public TagController(TagDao tagDao, ReceiptDao receipts) {
    this.tagDao = tagDao;
    this.receipts = receipts;
  }


  private ReceiptResponse mergeReceiptTags(ReceiptsRecord receiptsRecord) {
    int receiptId = receiptsRecord.getId();

    List<String> tags = receipts.getAllTagsForReceipt(receiptId).stream().map(TagsRecord::getTag).collect(toList());
    return new ReceiptResponse(receiptsRecord, tags);
  }

  @GET
  @Path("/{tag}")
  public List<ReceiptResponse> allReceipts(@PathParam("tag") String tagName) {
    List<ReceiptsRecord> receiptsRecords = tagDao.getAllReceiptsForTag(tagName);

    return receiptsRecords.stream()
        .map(this::mergeReceiptTags)
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
