package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.client;

import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.dto.HealthDTO;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.dto.YoutubeMetadataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("youtube-media")
public interface YoutubeMediaClient {
    @GetMapping("/health")
    HealthDTO getHealth();

    @GetMapping("api/video/info")
    YoutubeMetadataDTO getVideoInfo(
            @RequestParam("url") String url
    );
}
