package api;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AddTagRequest {
  @NotNull
  public int receiptId;
  public String tag;
}
