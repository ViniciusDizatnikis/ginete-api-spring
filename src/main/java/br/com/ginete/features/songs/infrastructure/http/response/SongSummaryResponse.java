package br.com.ginete.features.songs.infrastructure.http.response;

import br.com.ginete.features.songs.domain.Song;
import br.com.ginete.shared.http.ResponseFormats;

public record SongSummaryResponse(
        String id,
        String name,
        String image,
        String duration
) {
    public static SongSummaryResponse from(Song song) {
        return new SongSummaryResponse(
                song.getId().value(),
                song.getName(),
                song.getImage() != null ? song.getImage().toString() : null,
                ResponseFormats.duration(song.getDuration())
        );
    }
}
