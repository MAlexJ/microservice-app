package com.malexj.model.proxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malexj.model.api.BillsRequest;
import java.util.List;
import lombok.Data;

@Data
public class BillsProxyRequest {
  /** link to site url */
  @JsonProperty("url")
  private String url;

  /**
   * BillSearchModel data:
   *
   * <pre>
   *    BillSearchModel.session: 10
   *    BillSearchModel.registrationNumberCompareOperation: 2
   *    BillSearchModel.registrationNumber: 9672
   *    BillSearchModel.registrationRangeStart:
   *    BillSearchModel.registrationRangeEnd:
   *    BillSearchModel.name:
   *    BillSearchModel.detailView: false
   * </pre>
   */
  @JsonProperty("formUrlencodedData")
  private List<BillsRequest.FormUrlencodedData> formUrlencodedData;

  @Data
  public static class FormUrlencodedData {
    @JsonProperty("key")
    private String key;

    @JsonProperty("key")
    private String value;
  }
}
