package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.client;

import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.dto.DownloadMediaDTO;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.dto.HealthDTO;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.dto.YoutubeMetadataDTO;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.request.DownloadMediaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("youtube-media")
public interface YoutubeMediaClient {
    @GetMapping("/health")
    HealthDTO getHealth();

    @GetMapping("api/video/info")
    YoutubeMetadataDTO getVideoInfo(
            @RequestParam("url") String url
    );

    @PostMapping("api/video/download")
    DownloadMediaDTO downloadMedia(
        DownloadMediaRequest request
    );
}
