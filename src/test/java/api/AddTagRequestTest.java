package api;


import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;

import javax.validation.Validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class AddTagRequestTest {

  private final Validator validator = Validators.newValidator();

  @Test
  public void testValid() {
    AddTagRequest tag = new AddTagRequest();

    tag.receiptId = 1;
    tag.tag = "Food";
    assertThat(validator.validate(tag), empty());
  }

}