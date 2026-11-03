package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.http;

import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.application.HealthServiceUseCase;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.application.MetadataVideoUseCase;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.MediaHealth;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.YoutubeVideoMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/media", name = "Youtube Media")
@RequiredArgsConstructor
public class YoutubeMediaController {
    private final HealthServiceUseCase healthServiceUseCase;
    private final MetadataVideoUseCase metadataVideoUseCase;

    @GetMapping("/health")
    public ResponseEntity<MediaHealth> getHealth() {
        return ResponseEntity.ok(healthServiceUseCase.execute());
    }

    @GetMapping("video/info")
    public ResponseEntity<YoutubeVideoMetadata> getMetadataVideo(
            @RequestParam("url") String url
    ) {
        return ResponseEntity.ok(metadataVideoUseCase.execute(url));
    }
}
