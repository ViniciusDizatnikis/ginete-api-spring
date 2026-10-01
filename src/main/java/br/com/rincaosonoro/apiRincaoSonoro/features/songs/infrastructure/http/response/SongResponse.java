package br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.http.response;

import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.Song;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record SongResponse(
        String id,
        String name,
        String image,
        String duration,
        String artist,
        String audio,
        String createdAt
) {
    public static SongResponse from(Song song) {
        return new SongResponse(
                song.getId().value(),
                song.getName(),
                song.getImage() != null ? song.getImage().toString() : null,
                formatDuration(song.getDuration()),
                song.getArtist().value(),
                song.getAudio() != null ? song.getAudio().toString() : null,
                song.getCreatedAt() != null
                        ? DATE_FORMATTER.format(song.getCreatedAt())
                        : null
        );
    }

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    .withZone(ZoneId.of("America/Sao_Paulo"));

    private static String formatDuration(Duration duration) {
        if (duration == null) {
            return null;
        }

        long totalSeconds = duration.getSeconds();

        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
