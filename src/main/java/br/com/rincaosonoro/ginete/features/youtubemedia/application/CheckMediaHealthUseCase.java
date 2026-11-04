package br.com.rincaosonoro.ginete.features.youtubemedia.application;

import br.com.rincaosonoro.ginete.features.youtubemedia.domain.MediaHealth;
import br.com.rincaosonoro.ginete.features.youtubemedia.domain.YoutubeMediaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckMediaHealthUseCase {
    private final YoutubeMediaGateway youtubeMediaGateway;

    public MediaHealth execute() {
        return youtubeMediaGateway.check();
    }
}
