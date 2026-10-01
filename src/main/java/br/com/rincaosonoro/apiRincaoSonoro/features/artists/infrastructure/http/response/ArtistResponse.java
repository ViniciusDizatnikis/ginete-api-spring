package br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.http.response;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.Artist;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
                artist.getCreatedAt() != null ? DATE_FORMATTER.format(artist.getCreatedAt()) : null
        );
    }

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    .withZone(ZoneId.of("America/Sao_Paulo"));
}
