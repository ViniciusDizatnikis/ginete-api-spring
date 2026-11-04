package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest;

import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaHealth;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaId;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaServiceUnavailableException;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.YoutubeMediaGateway;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.YoutubeVideoMetadata;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.client.YoutubeMediaClient;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.request.AudioFormat;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.request.DownloadMediaRequest;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.request.MediaType;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignYoutubeMediaGateway implements YoutubeMediaGateway {
    private final YoutubeMediaClient youtubeMediaClient;

    @Override
    public MediaHealth check() {
        try {
            return youtubeMediaClient.getHealth().toDomain();
        } catch (FeignException exception) {
            throw new MediaServiceUnavailableException(exception);
        }
    }

    @Override
    public YoutubeVideoMetadata getMetadata(String url) {
        try {
            return youtubeMediaClient.getVideoInfo(url).toDomain();
        } catch (FeignException exception) {
            throw new MediaServiceUnavailableException(exception);
        }
    }

    @Override
    public MediaId downloadMedia(String url, String webhookUrl) {
        DownloadMediaRequest request = DownloadMediaRequest.builder()
                .url(url)
                .formatId("bestaudio")
                .type(MediaType.AUDIO)
                .audioFormat(AudioFormat.MP3)
                .webhookUrl(webhookUrl)
                .build();

        try {
            return youtubeMediaClient.downloadMedia(request).toDomain();
        } catch (FeignException exception) {
            throw new MediaServiceUnavailableException(exception);
        }
    }
}
