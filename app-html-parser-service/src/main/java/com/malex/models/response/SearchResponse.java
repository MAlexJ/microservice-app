package com.malex.models.response;

import com.malex.models.base.Bill;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResponse {
    private List<Bill> bills;
}
