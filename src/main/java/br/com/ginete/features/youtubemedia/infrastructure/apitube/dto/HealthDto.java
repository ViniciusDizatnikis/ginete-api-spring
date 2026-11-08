package br.com.ginete.features.youtubemedia.infrastructure.apitube.dto;

import br.com.ginete.features.youtubemedia.domain.MediaHealth;
import com.fasterxml.jackson.annotation.JsonProperty;

public record HealthDto(
        boolean success,
        String message,
        Data data
) {
    public MediaHealth toDomain() {
        return new MediaHealth(success, message);
    }

    public record Data(
            String status,

            @JsonProperty("ffmpeg_available")
            boolean ffmpegAvailable
    ) {
    }
}
