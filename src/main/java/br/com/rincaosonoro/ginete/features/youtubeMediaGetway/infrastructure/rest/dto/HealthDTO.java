package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.dto;

public record HealthDTO(
        boolean success,
        String message,
        Data data
) {
    public record Data(
            String status,
            boolean ffmpeg_available
    ){

    }
}
