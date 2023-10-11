package com.serjlemast.model.base;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class AbstractModel {
    protected final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }
}
