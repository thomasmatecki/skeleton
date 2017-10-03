package controllers;

import api.ReceiptSuggestionResponse;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.Feature;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    String merchantName = null;
    BigDecimal amount = null;

    try {
      Files.write(Paths.get("base64EncodedImage.dat"), base64EncodedImage.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

    /*Image img = Image.newBuilder().setContent(ByteString.copyFrom(Base64.getDecoder().decode(base64EncodedImage))).build();

    AnnotateImageRequest request = this.requestBuilder.setImage(img).build();

    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

      BatchAnnotateImagesResponse responses = client.batchAnnotateImages(Collections.singletonList(request));
      AnnotateImageResponse res = responses.getResponses(0);

      String merchantName = null;
      BigDecimal amount = null;


      // Your Algo Here!!
      // Sort text annotations by bounding polygon.  Top-most non-decimal text is the merchant
      // bottom-most decimal text is the total amount


      for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
        out.printf("Position : %s\n", annotation.getBoundingPoly());
        out.printf("Text: %s\n", annotation.getDescription());
      }*/

    //TextAnnotation fullTextAnnotation = res.getFullTextAnnotation();
    return new ReceiptSuggestionResponse(merchantName, amount);
  }
}

