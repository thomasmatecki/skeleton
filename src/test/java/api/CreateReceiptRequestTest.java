package api;


import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class CreateReceiptRequestTest {
  private final Validator validator = Validators.newValidator();

  @Test
  public void testValid() {
    CreateReceiptRequest receipt = new CreateReceiptRequest();

    receipt.merchant = "OK";
    receipt.amount = new BigDecimal(33.44);

    Set<ConstraintViolation<CreateReceiptRequest>> result = validator.validate(receipt);
    assertThat(result, empty());

  }

  @Test
  public void testMissingAmount() {
    CreateReceiptRequest receipt = new CreateReceiptRequest();
    receipt.merchant = "OK";


    //receipt.amount = new BigDecimal(33.44);
    assertThat(validator.validate(receipt), empty());
  }

  @Test
  public void testMissingMerchant() {

    // Setup
    CreateReceiptRequest receipt = new CreateReceiptRequest();
    receipt.amount = new BigDecimal(33.44);

    validator.validate(receipt);

    Set<ConstraintViolation<CreateReceiptRequest>> result = validator.validate(receipt);
    //Make sure state is expected
    assertThat(result, hasSize(1));
  }

}