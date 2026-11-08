package br.com.ginete.features.youtubemedia.infrastructure.apitube.dto;

import br.com.ginete.features.youtubemedia.domain.DownloadId;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record DownloadMediaDto(
        boolean success,
        String message,
        Data data
) {
    public DownloadId toDomain() {
        return new DownloadId(data.downloadId());
    }

    public record Data(
            @JsonProperty("download_id")
            String downloadId,
            String status,
            String title,
            Double percent,

            @JsonProperty("speed_bytes_per_second")
            Double speedBytesPerSecond,

            @JsonProperty("total_bytes")
            Long totalBytes,

            @JsonProperty("downloaded_bytes")
            Long downloadedBytes,

            @JsonProperty("eta_seconds")
            Long etaSeconds,

            @JsonProperty("error_code")
            String errorCode,

            @JsonProperty("error_message")
            String errorMessage,

            @JsonProperty("created_at")
            Instant createdAt,

            @JsonProperty("status_url")
            String statusUrl,

            @JsonProperty("file_url")
            String fileUrl,

            @JsonProperty("transfer_percent")
            Double transferPercent,

            @JsonProperty("transferred_bytes")
            Long transferredBytes,

            @JsonProperty("transfer_total_bytes")
            Long transferTotalBytes,

            @JsonProperty("stored_file_id")
            String storedFileId,

            @JsonProperty("retry_transfer_url")
            String retryTransferUrl
    ) {
    }
}
