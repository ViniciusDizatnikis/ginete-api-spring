package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.dto;

import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.YoutubeVideoMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record YoutubeMetadataDTO(
        boolean success,
        String message,
        Data data
) {
    public YoutubeVideoMetadata toDomain() {
        return new YoutubeVideoMetadata(
                data.videoId(),
                data.title(),
                data.originalUrl(),
                data.thumbnail(),
                new YoutubeVideoMetadata.Channel(
                        data.channel().id(),
                        data.channel().name(),
                        data.channel().url(),
                        data.channel().avatarUrl(),
                        data.channel().bannerUrl()
                ),
                data.durationSeconds(),
                data.publishedAt() != null ? LocalDate.parse(data.publishedAt()) : null,
                new YoutubeVideoMetadata.AudioFormats(
                        data.formats().audioOnly()
                                .stream()
                                .map(format -> new YoutubeVideoMetadata.Format(
                                        format.formatId(),
                                        format.ext(),
                                        format.resolution(),
                                        format.width(),
                                        format.height(),
                                        format.fps(),
                                        format.videoCodec(),
                                        format.audioCodec(),
                                        format.bitrateKbps(),
                                        format.filesizeBytes(),
                                        format.filesizeIsEstimate(),
                                        format.hasVideo(),
                                        format.hasAudio(),
                                        format.note()
                                ))
                                .toList()
                )
        );
    }


    public record Data(
            @JsonProperty("video_id")
            String videoId,
            String title,
            String description,

            @JsonProperty("original_url")
            String originalUrl,
            String thumbnail,
            Channel channel,

            @JsonProperty("duration_seconds")
            Integer durationSeconds,

            @JsonProperty("view_count")
            Long viewCount,

            @JsonProperty("published_at")
            String publishedAt,
            List<String> categories,

            @JsonProperty("is_live")
            Boolean isLive,
            Formats formats
    ) {
    }

    public record Channel(
            String id,
            String name,
            String handle,
            String url,

            @JsonProperty("avatar_url")
            String avatarUrl,

            @JsonProperty("banner_url")
            String bannerUrl,

            @JsonProperty("follower_count")
            Integer followerCount,

            @JsonProperty("is_verified")
            Boolean isVerified
    ) {
    }

    public record Formats(
            @JsonProperty("video_with_audio")
            List<Format> videoWithAudio,

            @JsonProperty("video_only")
            List<Format> videoOnly,

            @JsonProperty("audio_only")
            List<Format> audioOnly
    ) {
    }

    public record Format(
            @JsonProperty("format_id")
            String formatId,
            String ext,
            String resolution,
            Integer width,
            Integer height,
            Integer fps,

            @JsonProperty("video_codec")
            String videoCodec,

            @JsonProperty("audio_codec")
            String audioCodec,

            @JsonProperty("bitrate_kbps")
            Double bitrateKbps,

            @JsonProperty("filesize_bytes")
            Long filesizeBytes,

            @JsonProperty("filesize_is_estimate")
            Boolean filesizeIsEstimate,

            @JsonProperty("has_video")
            Boolean hasVideo,

            @JsonProperty("has_audio")
            Boolean hasAudio,
            String note
    ) {
    }
}