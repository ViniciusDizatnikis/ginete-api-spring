package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaType {
    VIDEO("video"),
    AUDIO("audio");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MediaType fromValue(String value) {
        for (MediaType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Tipo inválido: " + value);
    }
}