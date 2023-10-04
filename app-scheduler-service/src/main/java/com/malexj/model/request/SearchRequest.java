package com.malexj.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchRequest {

    /**
     * link: <a href="https://itd.rada.gov.ua/billInfo/Bills/searchResults">Link to search bills</a>
     */
    @JsonProperty("link")
    private String link;


    /**
     * The URL-encoded data sends encoded data to server, and uses the same encoding as that of the URL parameters.
     * Add x-www-form-urlencoded header to API request.
     * and pass key-value pairs for sending the request body to server
     */
    @JsonProperty("formUrlencodedData")
    private List<FormUrlencodedData> formUrlencodedData;

    /**
     * The application/x-www-form-urlencoded content type describes form data that is sent in a single block in the HTTP message body.
     * Unlike the query part of the URL in a GET request, the length of the data is unrestricted.
     * However, Media Server rejects requests that exceed the size specified by the configuration parameter MaxFileUploadSize.
     * <p>
     * example:
     * BillSearchModel.session: 10
     * BillSearchModel.registrationNumberCompareOperation: 2
     * BillSearchModel.registrationNumber: 9672
     * BillSearchModel.registrationRangeStart:
     * BillSearchModel.registrationRangeEnd:
     * BillSearchModel.name:
     * BillSearchModel.detailView: false
     */
    public record FormUrlencodedData(String key, String value) {
    }

}
