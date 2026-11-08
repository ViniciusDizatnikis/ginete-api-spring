package br.com.ginete.features.youtubemedia.infrastructure.apitube;

import br.com.ginete.features.youtubemedia.domain.DownloadId;
import br.com.ginete.features.youtubemedia.domain.MediaHealth;
import br.com.ginete.features.youtubemedia.domain.MediaRequestRejectedException;
import br.com.ginete.features.youtubemedia.domain.MediaServiceUnavailableException;
import br.com.ginete.features.youtubemedia.domain.YoutubeMediaGateway;
import br.com.ginete.features.youtubemedia.domain.YoutubeVideoMetadata;
import br.com.ginete.features.youtubemedia.infrastructure.apitube.client.YoutubeMediaClient;
import br.com.ginete.features.youtubemedia.infrastructure.apitube.request.AudioFormat;
import br.com.ginete.features.youtubemedia.infrastructure.apitube.request.DownloadMediaRequest;
import br.com.ginete.features.youtubemedia.infrastructure.apitube.request.DownloadMediaType;
import br.com.ginete.shared.exception.ApiException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class FeignYoutubeMediaGateway implements YoutubeMediaGateway {
    // Só estes indicam erro no pedido de quem chamou; 401, 403 e 429 são problema de chave ou limite nosso.
    private static final Set<Integer> REJECTED_STATUSES = Set.of(400, 404, 422);

    private final YoutubeMediaClient youtubeMediaClient;

    @Override
    public MediaHealth check() {
        try {
            return youtubeMediaClient.getHealth().toDomain();
        } catch (FeignException exception) {
            throw translate(exception);
        }
    }

    @Override
    public YoutubeVideoMetadata getMetadata(String url) {
        try {
            return youtubeMediaClient.getVideoInfo(url).toDomain();
        } catch (FeignException exception) {
            throw translate(exception);
        }
    }

    @Override
    public DownloadId downloadMedia(String url, String webhookUrl) {
        DownloadMediaRequest request = DownloadMediaRequest.builder()
                .url(url)
                .formatId("bestaudio")
                .type(DownloadMediaType.AUDIO)
                .audioFormat(AudioFormat.MP3)
                .webhookUrl(webhookUrl)
                .build();

        try {
            return youtubeMediaClient.downloadMedia(request).toDomain();
        } catch (FeignException exception) {
            throw translate(exception);
        }
    }

    private static ApiException translate(FeignException exception) {
        if (REJECTED_STATUSES.contains(exception.status())) {
            return new MediaRequestRejectedException(exception);
        }

        return new MediaServiceUnavailableException(exception);
    }
}
