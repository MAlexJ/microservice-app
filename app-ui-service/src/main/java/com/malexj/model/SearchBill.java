package com.malexj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchBill {

    @JsonProperty("bills")
    private List<Bill> bills;
}
