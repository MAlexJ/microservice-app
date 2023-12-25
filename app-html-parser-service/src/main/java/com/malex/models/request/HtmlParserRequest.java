package com.malex.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Json Request with site url and xpath parameters
 *
 * <p>Parameters:
 *
 * <p>1. url - site url
 *
 * <p>2. xpath - xpath query selector
 */
@Data
public class HtmlParserRequest {

  /** Url to site */
  @JsonProperty("url")
  private String url;

  /** xpath – XPath expression */
  @JsonProperty("xpath")
  private String xpath;
}
