package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import api.ReceiptTagsResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

//import dao.ReceiptDao;

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {

  final ReceiptDao receipts;

  public ReceiptController(ReceiptDao receipts) {
    this.receipts = receipts;
  }

  @POST
  public ReceiptResponse createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {

    int id = receipts.insert(receipt.merchant, receipt.amount);
    ReceiptsRecord receiptsRecord = receipts.getOneReceipt(id);

    return new ReceiptResponse(receiptsRecord, Collections.emptyList());

  }


  public ReceiptResponse mergeReceiptTags(ReceiptsRecord receiptsRecord) {
    int receiptId = receiptsRecord.getId();
    List<String> tags = receipts.getAllTagsForReceipt(receiptId).stream().map(TagsRecord::getTag).collect(toList());
    return new ReceiptResponse(receiptsRecord, tags);
  }

  @GET
  public List<ReceiptResponse> getReceipts() {
    List<ReceiptsRecord> receiptRecords = receipts.getAllReceipts();
    return receiptRecords.stream().map(this::mergeReceiptTags).collect(toList());
  }


  @GET
  @Path("/{id}")
  public ReceiptTagsResponse getTags(@PathParam("id") int receiptId) {
    List<String> tags = receipts.getAllTagsForReceipt(receiptId).stream().map(TagsRecord::getTag).collect(toList());
    return new ReceiptTagsResponse(receiptId, tags);
  }

}
