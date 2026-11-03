package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest;

import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.MediaHealth;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.MediaServiceUnavailableException;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.YoutubeMediaRepository;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.YoutubeVideoMetadata;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.client.YoutubeMediaClient;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.dto.HealthDTO;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.dto.YoutubeMetadataDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class YoutubeMediaProvider implements YoutubeMediaRepository {
    private final YoutubeMediaClient youtubeMediaClient;


    @Override
    public MediaHealth check() {
        try {
            HealthDTO healthDTO = youtubeMediaClient.getHealth();

            return new MediaHealth(
                    healthDTO.success(),
                    healthDTO.message()
            );
        } catch (FeignException exception) {
            throw new MediaServiceUnavailableException(exception);
        }
    }

    @Override
    public YoutubeVideoMetadata getMetadata(String url) {
        try {
            YoutubeMetadataDTO youtubeMetadataDTO = youtubeMediaClient.getVideoInfo(url);

            return youtubeMetadataDTO.toDomain();
        } catch (FeignException exception) {
            throw new MediaServiceUnavailableException(exception);
        }
    }
}