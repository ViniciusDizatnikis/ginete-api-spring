package br.com.ginete.features.songs.domain;

import br.com.ginete.features.artists.domain.ArtistId;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
public class Song {
    private SongId id;
    private URI image;
    private String name;
    private Duration duration;
    private ArtistId artist;
    private URI audio;
    private boolean featured;
    private Instant createdAt;
    private Instant updatedAt;

    public Song() {
    }
}
