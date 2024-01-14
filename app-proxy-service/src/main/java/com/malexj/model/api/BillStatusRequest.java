package com.malexj.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** API Json request representation */
@Data
public class BillStatusRequest {

  /** Url to site */
  @JsonProperty("url")
  private String url;
}
