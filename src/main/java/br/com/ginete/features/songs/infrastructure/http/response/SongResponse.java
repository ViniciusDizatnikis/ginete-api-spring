package br.com.ginete.features.songs.infrastructure.http.response;

import br.com.ginete.features.songs.domain.Song;
import br.com.ginete.shared.http.ResponseFormats;

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
                ResponseFormats.duration(song.getDuration()),
                song.getArtist() != null ? song.getArtist().value() : null,
                song.getAudio() != null ? song.getAudio().toString() : null,
                ResponseFormats.dateTime(song.getCreatedAt())
        );
    }
}
