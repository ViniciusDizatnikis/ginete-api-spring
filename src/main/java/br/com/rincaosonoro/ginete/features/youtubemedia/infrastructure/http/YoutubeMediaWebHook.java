package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class YoutubeMediaWebHook {
    static final String DOWNLOAD_WEBHOOK_PATH = "/v1/media/webhook/download";

    private static final Logger log = LoggerFactory.getLogger(YoutubeMediaWebHook.class);

    @PostMapping(DOWNLOAD_WEBHOOK_PATH)
    public ResponseEntity<Void> receiveDownloadProgress(
            @RequestBody Map<String, Object> data
    ) {
        log.info("Webhook de download recebido: {}", data);

        return ResponseEntity.ok().build();
    }
}
