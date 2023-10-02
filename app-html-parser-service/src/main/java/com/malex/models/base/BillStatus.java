package com.malex.models.base;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BillStatus extends AbstractModel {
    private LocalDate data;
    private String status;

    public static class BillStatusBuilder {
        public BillStatusBuilder data(String date) {
            this.data = parseDate(date);
            return this;
        }
    }

}
