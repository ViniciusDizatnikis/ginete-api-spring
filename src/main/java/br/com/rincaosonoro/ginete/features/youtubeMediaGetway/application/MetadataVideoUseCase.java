package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.application;

import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.YoutubeVideoMetadata;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.YoutubeMediaProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetadataVideoUseCase {
    private final YoutubeMediaProvider youtubeMediaProvider;

    public YoutubeVideoMetadata execute(String url) {
        return youtubeMediaProvider.getMetadata(url);
    }
}
