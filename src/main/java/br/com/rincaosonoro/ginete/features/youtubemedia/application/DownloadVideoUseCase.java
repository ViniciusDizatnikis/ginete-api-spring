package br.com.rincaosonoro.ginete.features.youtubemedia.application;

import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaId;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.YoutubeMediaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadVideoUseCase {
    private final YoutubeMediaGateway youtubeMediaGateway;

    public MediaId execute(String url, String webhookUrl) {
        return youtubeMediaGateway.downloadMedia(url, webhookUrl);
    }
}
