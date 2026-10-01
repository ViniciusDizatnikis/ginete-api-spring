package br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistId;
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
    private Instant createdAt;
    private Instant updatedAt;


    public Song() {
    }

    public Song(SongId id, URI image, String name, Duration duration, ArtistId artist, URI audio, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.duration = duration;
        this.artist = artist;
        this.audio = audio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
