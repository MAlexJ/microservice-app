package com.malex.model.proxy.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillStatusesProxyRequest {

  /** Url to site */
  private String url;
}
