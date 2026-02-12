package br.com.higia.domain.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {
    private InstantUtils() {}

    private static final ZoneId zone = ZoneId.of("America/Sao_Paulo");

    public static Instant now() {
        return Instant.now().minus(3, ChronoUnit.HOURS);
    }

    public static Instant parseInstant(final LocalDate value) {
        if (value == null) {
            return null;
        }
        return value.atStartOfDay(zone).toInstant();
    }
}
