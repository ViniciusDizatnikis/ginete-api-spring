package br.com.ginete.features.youtubemedia.application;

import br.com.ginete.features.youtubemedia.domain.DownloadId;
import br.com.ginete.features.youtubemedia.domain.YoutubeMediaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartDownloadUseCase {
    private final YoutubeMediaGateway youtubeMediaGateway;

    public DownloadId execute(String url, String webhookUrl) {
        return youtubeMediaGateway.downloadMedia(url, webhookUrl);
    }
}
