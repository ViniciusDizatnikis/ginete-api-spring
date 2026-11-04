package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.dto;

import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaHealth;
import com.fasterxml.jackson.annotation.JsonProperty;

public record HealthDTO(
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
