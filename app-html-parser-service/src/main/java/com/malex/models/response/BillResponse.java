package com.malex.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.models.base.BillStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class BillResponse {

    @JsonProperty("link")
    private String link;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number")
    private String number;

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;

    @JsonProperty("statuses")
    private List<BillStatus> statuses;
}
