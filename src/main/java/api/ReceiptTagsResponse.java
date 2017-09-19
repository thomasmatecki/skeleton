package api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReceiptTagsResponse {

  @JsonProperty
  Integer id;

  @JsonProperty
  List<String> tags;

  public ReceiptTagsResponse(Integer id, List<String> tags) {
    this.id = id;
    this.tags = tags;
  }
}
