package api;

import dao.ReceiptDao;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;
import io.dropwizard.jersey.validation.Validators;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

public class ReceiptDaoTest extends AbstractDaoTest {

  private final Validator validator = Validators.newValidator();
  private final TagDao tagDao = new TagDao(jooqConfig);
  private final ReceiptDao receiptDao = new ReceiptDao(jooqConfig);


  @Test
  public void testInsertThenTag() {

    int receiptId = receiptDao.insert("Bread and Butter", new BigDecimal(6.78));

    tagDao.insert(receiptId, "Food");
    tagDao.insert(receiptId, "Reuben");

    List<ReceiptsRecord> receipts = receiptDao.getAllReceipts();
    Assert.assertEquals(receipts.size(), 1);
    Assert.assertTrue(tagDao.exists(receiptId, "Food"));
    Assert.assertTrue(tagDao.exists(receiptId, "Reuben"));

    Assert.assertEquals(tagDao.getAllReceiptsForTag("Food").size(), 1);
    Assert.assertEquals(tagDao.getAllReceiptsForTag("Reuben").size(), 1);

  }


}