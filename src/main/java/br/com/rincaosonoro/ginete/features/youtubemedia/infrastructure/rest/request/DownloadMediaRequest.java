package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DownloadMediaRequest(
        String url,

        @JsonProperty("format_id")
        String formatId,

        MediaType type,

        @JsonProperty("video_format_id")
        String videoFormatId,

        @JsonProperty("audio_format_id")
        String audioFormatId,

        @JsonProperty("audio_format")
        AudioFormat audioFormat,

        @JsonProperty("video_format")
        VideoFormat videoFormat,

        @JsonProperty("webhook_url")
        String webhookUrl
) {
}
