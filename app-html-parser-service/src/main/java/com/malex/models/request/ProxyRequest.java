package com.malex.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProxyRequest {

  /** Link to site URL */
  @JsonProperty("url")
  private String url;
}
