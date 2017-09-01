package controllers;

import api.CreateReceiptRequest;
import dao.ReceiptDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
  final ReceiptDao receipts;

  public ReceiptController(ReceiptDao receipts) {
    this.receipts = receipts;
  }

  @POST
  public int createReceipt(/*@Valid */CreateReceiptRequest receipt) {
    return 99;
    //return receipts.insert(receipt.merchantName, receipt.value);
  }

  @GET
  public int getReceipt(){
    return 999;
  }

}
