package br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest;

import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaServiceUnavailableException;
import br.com.rincaosonoro.ginete.features.youtubemedia.infrastructure.rest.client.YoutubeMediaClient;
import feign.FeignException;
import feign.Request;
import feign.RetryableException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeignYoutubeMediaGatewayTests {
    private static final Request REQUEST = Request.create(
            Request.HttpMethod.GET,
            "http://youtube-media",
            Map.of(),
            null,
            StandardCharsets.UTF_8,
            null
    );

    private final YoutubeMediaClient client = mock(YoutubeMediaClient.class);
    private final FeignYoutubeMediaGateway provider = new FeignYoutubeMediaGateway(client);

    @Test
    void connectionFailureOnMetadataBecomesUnavailable() {
        FeignException failure = new RetryableException(
                -1, "connection refused", Request.HttpMethod.GET, (Long) null, REQUEST
        );
        when(client.getVideoInfo("url")).thenThrow(failure);

        var exception = assertThrows(MediaServiceUnavailableException.class, () -> provider.getMetadata("url"));

        assertSame(failure, exception.getCause());
    }

    @Test
    void upstreamErrorOnMetadataBecomesUnavailable() {
        FeignException failure = new FeignException.InternalServerError("boom", REQUEST, null, Map.of());
        when(client.getVideoInfo("url")).thenThrow(failure);

        var exception = assertThrows(MediaServiceUnavailableException.class, () -> provider.getMetadata("url"));

        assertSame(failure, exception.getCause());
    }

    @Test
    void upstreamErrorOnHealthBecomesUnavailable() {
        FeignException failure = new FeignException.BadGateway("boom", REQUEST, null, Map.of());
        when(client.getHealth()).thenThrow(failure);

        var exception = assertThrows(MediaServiceUnavailableException.class, provider::check);

        assertSame(failure, exception.getCause());
    }
}
