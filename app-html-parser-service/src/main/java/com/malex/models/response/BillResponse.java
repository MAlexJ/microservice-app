package com.malex.models.response;

import com.malex.models.base.BillStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BillResponse {
    private String link;
    private String name;
    private String number;
    private LocalDate registrationDate;
    private List<BillStatus> statuses;
}
