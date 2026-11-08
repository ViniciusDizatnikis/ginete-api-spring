package br.com.ginete.features.songs.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record SongId(String value) {
    @JsonValue
    public String value() {
        return value;
    }
}
