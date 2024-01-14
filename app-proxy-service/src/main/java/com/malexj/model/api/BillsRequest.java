package com.malexj.model.api;

import java.util.List;
import lombok.Data;

@Data
public class BillsRequest {
  /** link to site url */
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
  private List<FormUrlencodedData> formUrlencodedData;

  @Data
  public static class FormUrlencodedData {

    private String key;

    private String value;
  }
}
