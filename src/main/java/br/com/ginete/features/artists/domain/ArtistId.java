package br.com.ginete.features.artists.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record ArtistId(String value) {

    @JsonValue
    public String value() {
        return value;
    }
}
