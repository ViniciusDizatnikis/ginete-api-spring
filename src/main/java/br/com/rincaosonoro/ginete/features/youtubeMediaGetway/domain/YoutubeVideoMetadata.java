package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain;

import java.time.LocalDate;
import java.util.List;

public record YoutubeVideoMetadata(
        String videoId,
        String title,
        String originalURL,
        String thumbnail,
        Channel channel,
        Integer durationSeconds,
        LocalDate publishedAt,
        AudioFormats audioFormats
) {
    public record Channel(
            String id,
            String name,
            String url,
            String avatarURL,
            String bannerURL
    ) {
    }

    public record AudioFormats(
            List<Format> audio_only
    ) {
    }

    public record Format(
            String formatId,
            String ext,
            String resolution,
            Integer width,
            Integer height,
            Integer fps,
            String videoCodec,
            String audioCodec,
            Double bitrateKbps,
            Long filesizeBytes,
            boolean filesizeIsEstimate,
            boolean hasVideo,
            boolean hasAudio,
            String note
    ) {
    }
}
