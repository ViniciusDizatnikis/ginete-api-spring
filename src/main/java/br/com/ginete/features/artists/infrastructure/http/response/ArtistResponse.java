package br.com.ginete.features.artists.infrastructure.http.response;

import br.com.ginete.features.artists.domain.Artist;
import br.com.ginete.shared.http.ResponseFormats;

public record ArtistResponse(
        String id,
        String name,
        String image,
        String banner,
        String createdAt
) {
    public static ArtistResponse from(Artist artist) {
        return new ArtistResponse(
                artist.getId().value(),
                artist.getName(),
                artist.getImage() != null ? artist.getImage().toString() : null,
                artist.getBanner() != null ? artist.getBanner().toString() : null,
                ResponseFormats.dateTime(artist.getCreatedAt())
        );
    }
}
