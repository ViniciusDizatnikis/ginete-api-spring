package br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.time.Instant;

@Getter
@Setter
public class Artist {
    private ArtistId id;
    private String name;
    private boolean featured;
    private URI image;
    private URI banner;
    private Instant createdAt;
    private Instant updatedAt;

    public Artist() {
    }

    public Artist(ArtistId id, String name, boolean featured, URI image, URI banner, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.featured = featured;
        this.image = image;
        this.banner = banner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
