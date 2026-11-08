package br.com.ginete.features.youtubemedia.infrastructure.apitube.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VideoFormat {
    MP4("mp4"),
    MKV("mkv"),
    WEBM("webm");

    private final String value;

    VideoFormat(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static VideoFormat fromValue(String value) {
        for (VideoFormat format : values()) {
            if (format.value.equalsIgnoreCase(value)) {
                return format;
            }
        }

        throw new IllegalArgumentException("Invalid video format: " + value);
    }
}
