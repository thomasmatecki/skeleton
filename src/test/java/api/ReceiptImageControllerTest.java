package api;

import controllers.ReceiptImageController;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReceiptImageControllerTest {

  ReceiptImageController controller = new ReceiptImageController();

  @Test
  public void testParseReceipt() {

    try {
      byte[] imageBytes = Files.readAllBytes(Paths.get("base64EncodedImage.dat"));

      String base64EncodedImage = new String(imageBytes);
      Assert.assertNotNull(base64EncodedImage);

      ReceiptSuggestionResponse result = controller.parseReceipt(base64EncodedImage);

      Assert.assertEquals(new BigDecimal("2.18"), result.amount);
      Assert.assertEquals("Cornel1 Tech", result.merchantName);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
