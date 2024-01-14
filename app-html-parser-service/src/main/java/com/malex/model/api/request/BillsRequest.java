package com.malex.model.api.request;

import java.util.List;
import lombok.Data;

/**
 * Example:
 *
 * <pre>
 *     <code>{
 *        "url":"https://itd.rada.gov.ua/billInfo/Bills/searchResults",
 *        "formUrlencodedData":[
 *            {"key":"BillSearchModel.registrationNumber", "value":"9672" },
 *            {"key":"BillSearchModel.registrationNumberCompareOperation","value":"2" }
 *        ]
 *      }
 *     </code>
 * </pre>
 */
@Data
public class BillsRequest {

  /** link to site url for search */
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
