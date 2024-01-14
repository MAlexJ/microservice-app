package com.malexj.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malexj.model.ResponseState;
import java.util.List;
import lombok.Data;

@Data
public class RestApiResponse {
  @JsonProperty("status")
  private String status;

  @JsonProperty("statusCode")
  private int statusCode;

  @JsonProperty("headers")
  private ProxyHeaders headers;

  @JsonProperty("body")
  private ProxyBody body;

  @JsonProperty("state")
  private ResponseState state;

  public ResponseState getState() {
    if (state == null) {
      return ResponseState.SERVICE;
    }
    return state;
  }

  @Data
  public static class ProxyBody {

    @JsonProperty("Data")
    private String data;

    @Override
    public String toString() {
      return "body:" + (getData().length() < 100 ? getData() : data.substring(0, 100));
    }
  }

  @Data
  public static class ProxyHeaders {

    @JsonProperty("Content-Type")
    private List<String> contentType;

    @JsonProperty("Vary")
    private List<String> vary;
  }
}
