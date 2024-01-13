package com.malex.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.models.base.ResponseState;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import lombok.Data;

/**
 * Representation JSON API model:
 *
 * <pre>
 *     <code>
 *          1. "status": "200 OK",
 *          2. "statusCode": 200,
 *          3. "contentLength": -1,
 *          4. "headers":
 *          4.1. "Date": ["Fri, 22 Dec 2023 07:33:34 GMT"]
 *          4.2. "Content-Type": ["text/html; charset=utf-8"],
 *          4.3. "Vary": ["Accept-Encoding"],
 *          4.4. "Server":  ["Microsoft-IIS/10.0"],
 *          4.5. "Strict-Transport-Security": ["max-age=2592000"]
 *          5. "body".
 *          5.1. "body"."Subtype": 0
 *          5.2. "body"."Data": ".....base64....encoding...."
 * </code>
 * </pre>
 */
@Data
public class ProxyResponse {

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

  public String getHtmlAsText() {
    return Optional.ofNullable(getBody())
        .map(ProxyBody::getData)
        .map(data -> Base64.getDecoder().decode(data))
        .map(String::new)
        .orElseThrow();
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
