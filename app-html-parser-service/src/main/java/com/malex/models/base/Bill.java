package com.malex.models.base;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Bill extends AbstractModel {
    private String link;
    private String name;
    private String number;
    private LocalDate registrationDate;

    public static class BillBuilder {
        public BillBuilder registrationDate(String date) {
            this.registrationDate = parseDate(date);
            return this;
        }
    }

}
