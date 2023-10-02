package com.malex.models.request;

import com.malex.models.base.FormUrlencodedData;
import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {

    /**
     * link: https://itd.rada.gov.ua/billInfo/Bills/searchResults
     */
    private String link;

    /**
     * BillSearchModel.session: 10
     * BillSearchModel.registrationNumberCompareOperation: 2
     * BillSearchModel.registrationNumber: 9672
     * BillSearchModel.registrationRangeStart:
     * BillSearchModel.registrationRangeEnd:
     * BillSearchModel.name:
     * BillSearchModel.detailView: false
     */
    private List<FormUrlencodedData> formUrlencodedData;

}
