package br.com.rincaosonoro.ginete.features.artists.domain;

import br.com.rincaosonoro.ginete.shared.exception.NotFoundException;

public class ArtistNotFoundException extends NotFoundException {

    public ArtistNotFoundException(ArtistId id) {
        super("Artist Not Found", "Artist not found: " + id.value());
    }
}
