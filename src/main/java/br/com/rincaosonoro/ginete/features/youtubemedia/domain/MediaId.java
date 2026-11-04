package br.com.rincaosonoro.ginete.features.youtubemedia.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record MediaId(String value) {

    @JsonValue
    public String value() {
        return value;
    }

}
