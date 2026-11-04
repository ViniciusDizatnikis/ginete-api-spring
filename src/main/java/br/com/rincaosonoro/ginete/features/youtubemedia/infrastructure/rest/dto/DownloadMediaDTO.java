package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.dto;

import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaId;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record DownloadMediaDTO(
        boolean success,
        String message,
        Data data
) {
    public MediaId toDomain() {
        return new MediaId(data.downloadId());
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
