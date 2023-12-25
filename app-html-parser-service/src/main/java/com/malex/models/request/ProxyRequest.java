package com.malex.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProxyRequest {

  /** Url to site */
  @JsonProperty("url")
  private String url;
}
