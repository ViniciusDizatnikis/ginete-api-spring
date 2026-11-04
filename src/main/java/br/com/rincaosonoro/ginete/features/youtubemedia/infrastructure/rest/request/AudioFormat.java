package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AudioFormat {
    MP3("mp3");

    private final String value;

    AudioFormat(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AudioFormat fromValue(String value) {
        for (AudioFormat format : values()) {
            if (format.value.equalsIgnoreCase(value)) {
                return format;
            }
        }

        throw new IllegalArgumentException("Formato de áudio inválido: " + value);
    }
}