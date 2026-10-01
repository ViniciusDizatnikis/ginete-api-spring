package br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain;

public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException(ArtistId id) {
        super("Artist not found: " + id.value());
    }
}