package br.com.higia.domain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateUtils() {
    }

    public static LocalDate parse(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, FORMATTER);
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(FORMATTER);
    }
}
