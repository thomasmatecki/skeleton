package controllers;

import api.ReceiptSuggestionResponse;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

@Path("/images")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptImageController {
  private final AnnotateImageRequest.Builder requestBuilder;

  public ReceiptImageController() {
    // DOCUMENT_TEXT_DETECTION is not the best or only OCR method available

    Feature ocrFeature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();

    this.requestBuilder = AnnotateImageRequest.newBuilder().addFeatures(ocrFeature);


  }

  /**
   * This borrows heavily from the Google Vision API Docs.  See:
   * https://cloud.google.com/vision/docs/detecting-fulltext
   * <p>
   * YOU SHOULD MODIFY THIS METHOD TO RETURN A ReceiptSuggestionResponse:
   * <p>
   * public class ReceiptSuggestionResponse {
   * String merchantName;
   * String amount;
   * }
   */
  @POST
  public ReceiptSuggestionResponse parseReceipt(@NotEmpty String base64EncodedImage) throws Exception {

    Image img = Image.newBuilder().setContent(ByteString.copyFrom(Base64.getDecoder().decode(base64EncodedImage))).build();

    AnnotateImageRequest request = this.requestBuilder.setImage(img).build();

    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

      BatchAnnotateImagesResponse responses = client.batchAnnotateImages(Collections.singletonList(request));
      AnnotateImageResponse res = responses.getResponses(0);

      String text = res.getFullTextAnnotation().getText();

      String merchantName = text.split("\\n")[0];

      Pattern amountPattern = Pattern.compile("^(\\d*[.]\\d\\d*)", Pattern.MULTILINE);

      Matcher amountMatches = amountPattern.matcher(text);
      String lastAmount = null;

      while (amountMatches.find()) {
        lastAmount = amountMatches.group();
      }

      BigDecimal amount = new BigDecimal(lastAmount);


      return new ReceiptSuggestionResponse(merchantName, amount);

    } /*catch (IndexOutOfBoundsException iob) {


    }*/

  }
}