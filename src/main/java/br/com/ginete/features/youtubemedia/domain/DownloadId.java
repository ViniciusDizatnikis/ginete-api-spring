package br.com.ginete.features.youtubemedia.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record DownloadId(String value) {

    @JsonValue
    public String value() {
        return value;
    }

}
