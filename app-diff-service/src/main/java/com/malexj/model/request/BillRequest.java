package com.malexj.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malexj.model.BillStatus;
import com.malexj.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillRequest {

    @JsonProperty("user")
    private User user;

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
