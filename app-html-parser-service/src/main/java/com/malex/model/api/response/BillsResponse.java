package com.malex.model.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.malex.model.base.Bill;

import java.util.Collections;
import java.util.List;

public record BillsResponse(@JsonProperty("bills") List<Bill> bills) {

    public static BillsResponse buildEmpty(){
       return new BillsResponse(Collections.emptyList());
    }
}
