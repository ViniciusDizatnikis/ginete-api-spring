package br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.http.response;

import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.Song;

import java.time.Duration;

public record TopSongResponse(
        String id,
        String name,
        String image,
        String duration
) {
    public static TopSongResponse from(Song song) {
        return new TopSongResponse(
                song.getId().value(),
                song.getName(),
                song.getImage() != null ? song.getImage().toString() : null,
                formatDuration(song.getDuration())
        );
    }

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
