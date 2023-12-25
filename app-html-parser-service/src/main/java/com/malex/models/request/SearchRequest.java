package com.malex.models.request;

import com.malex.models.base.FormUrlencodedData;
import java.util.List;
import lombok.Data;

/**
 * Example:
 *
 * <pre>
 *     <code>{
 *        "link":"https://itd.rada.gov.ua/billInfo/Bills/searchResults",
 *        "formUrlencodedData":[
 *            {"key":"BillSearchModel.registrationNumber", "value":"9672" },
 *            {"key":"BillSearchModel.registrationNumberCompareOperation","value":"2" }
 *        ]
 *      }
 *     </code>
 * </pre>
 */
@Data
public class SearchRequest {

  /**
   * link to site url:
   *
   * <p>link: https://itd.rada.gov.ua/billInfo/Bills/searchResults
   */
  private String link;

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
}
