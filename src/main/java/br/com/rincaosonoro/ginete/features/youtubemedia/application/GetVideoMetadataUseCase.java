package br.com.rincaosonoro.ginete.features.youtubemedia.application;

import br.com.rincaosonoro.ginete.features.youtubemedia.domain.YoutubeMediaGateway;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.YoutubeVideoMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetVideoMetadataUseCase {
    private final YoutubeMediaGateway youtubeMediaGateway;

    public YoutubeVideoMetadata execute(String url) {
        return youtubeMediaGateway.getMetadata(url);
    }
}
