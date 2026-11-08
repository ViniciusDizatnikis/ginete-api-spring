package br.com.ginete.features.youtubemedia.infrastructure.apitube.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DownloadMediaType {
    VIDEO("video"),
    AUDIO("audio");

    private final String value;

    DownloadMediaType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DownloadMediaType fromValue(String value) {
        for (DownloadMediaType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid media type: " + value);
    }
}