package com.malex.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProxyRequest {

  /** Url to site */
  @JsonProperty("url")
  private String url;
}
