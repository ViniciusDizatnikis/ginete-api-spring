package br.com.ginete.shared.http;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Formatos de data e duração usados nas respostas do catálogo.
 */
public final class ResponseFormats {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    .withZone(ZoneId.of("America/Sao_Paulo"));

    private ResponseFormats() {
    }

    public static String dateTime(Instant instant) {
        return instant != null ? DATE_TIME_FORMATTER.format(instant) : null;
    }

    /**
     * "mm:ss".
     */
    public static String duration(Duration duration) {
        if (duration == null) {
            return null;
        }

        long totalSeconds = duration.getSeconds();

        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
