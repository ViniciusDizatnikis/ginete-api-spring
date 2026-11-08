package br.com.ginete.features.youtubemedia.infrastructure.http;

import br.com.ginete.features.youtubemedia.application.CheckMediaHealthUseCase;
import br.com.ginete.features.youtubemedia.application.GetVideoMetadataUseCase;
import br.com.ginete.features.youtubemedia.application.StartDownloadUseCase;
import br.com.ginete.features.youtubemedia.domain.DownloadId;
import br.com.ginete.features.youtubemedia.domain.MediaHealth;
import br.com.ginete.features.youtubemedia.domain.YoutubeVideoMetadata;
import br.com.ginete.features.youtubemedia.infrastructure.http.request.DownloadVideoRequest;
import br.com.ginete.features.youtubemedia.infrastructure.http.webhook.DownloadWebhookController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static br.com.ginete.shared.config.openAPIConfig.TAG_MEDIA;

@RestController
@RequestMapping("/v1/media")
@Tag(name = TAG_MEDIA)
@RequiredArgsConstructor
public class YoutubeMediaController {
    private final CheckMediaHealthUseCase checkMediaHealthUseCase;
    private final GetVideoMetadataUseCase getVideoMetadataUseCase;
    private final StartDownloadUseCase startDownloadUseCase;

    @GetMapping("/health")
    public ResponseEntity<MediaHealth> getHealth() {
        return ResponseEntity.ok(checkMediaHealthUseCase.execute());
    }

    @GetMapping("/video/info")
    public ResponseEntity<YoutubeVideoMetadata> getVideoMetadata(
            @RequestParam("url") String url
    ) {
        return ResponseEntity.ok(getVideoMetadataUseCase.execute(url));
    }

    @PostMapping("/get/media")
    public ResponseEntity<DownloadId> startDownload(
            @RequestBody DownloadVideoRequest request
    ) {
        String webhookUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(DownloadWebhookController.DOWNLOAD_WEBHOOK_PATH)
                .toUriString();

        DownloadId downloadId = startDownloadUseCase.execute(request.url(), webhookUrl);
        return ResponseEntity.ok(downloadId);
    }
}
