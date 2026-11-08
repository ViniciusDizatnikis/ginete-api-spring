package br.com.ginete.features.artists.domain;

import br.com.ginete.shared.exception.NotFoundException;

public class ArtistNotFoundException extends NotFoundException {

    public ArtistNotFoundException(ArtistId id) {
        super("Artist Not Found", "Artist not found: " + id.value());
    }
}
