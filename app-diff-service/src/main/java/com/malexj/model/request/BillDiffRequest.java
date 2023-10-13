package com.malexj.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malexj.model.BillStatus;
import com.malexj.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BillDiffRequest {

    @JsonProperty("user")
    private User user;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number")
    private String number;

    @JsonProperty("statuses")
    private List<BillStatus> diffStatuses;

}
