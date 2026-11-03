package br.com.rincaosonoro.ginete.features.artists.domain;

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
}
