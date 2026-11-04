package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.http;

import br.com.rincaosonoro.ginete.features.youtubemedia.application.CheckMediaHealthUseCase;
import br.com.rincaosonoro.ginete.features.youtubemedia.application.DownloadVideoUseCase;
import br.com.rincaosonoro.ginete.features.youtubemedia.application.GetVideoMetadataUseCase;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaHealth;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaId;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.YoutubeVideoMetadata;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.http.request.DownloadVideoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/v1/media", name = "Youtube Media")
@RequiredArgsConstructor
public class YoutubeMediaController {
    private final CheckMediaHealthUseCase checkMediaHealthUseCase;
    private final GetVideoMetadataUseCase getVideoMetadataUseCase;
    private final DownloadVideoUseCase downloadVideoUseCase;

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
    public ResponseEntity<MediaId> startDownload(
            @RequestBody DownloadVideoRequest request
    ) {
        String webhookUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(YoutubeMediaWebHook.DOWNLOAD_WEBHOOK_PATH)
                .toUriString();

        MediaId mediaId = downloadVideoUseCase.execute(request.url(), webhookUrl);
        return ResponseEntity.ok(mediaId);
    }
}
