package br.com.ginete.features.youtubemedia.infrastructure.apitube.client;

import br.com.ginete.features.youtubemedia.infrastructure.apitube.dto.DownloadMediaDto;
import br.com.ginete.features.youtubemedia.infrastructure.apitube.dto.HealthDto;
import br.com.ginete.features.youtubemedia.infrastructure.apitube.dto.YoutubeMetadataDto;
import br.com.ginete.features.youtubemedia.infrastructure.apitube.request.DownloadMediaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("youtube-media")
public interface YoutubeMediaClient {
    @GetMapping("/health")
    HealthDto getHealth();

    @GetMapping("/api/video/info")
    YoutubeMetadataDto getVideoInfo(
            @RequestParam("url") String url
    );

    @PostMapping("/api/video/download")
    DownloadMediaDto downloadMedia(
        DownloadMediaRequest request
    );
}
