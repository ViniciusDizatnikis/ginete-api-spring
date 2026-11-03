package br.com.rincaosonoro.ginete.features.youtubeMediaGetway.application;

import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.domain.MediaHealth;
import br.com.rincaosonoro.ginete.features.youtubeMediaGetway.infrastructure.rest.YoutubeMediaProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthServiceUseCase {
    private final YoutubeMediaProvider youtubeMediaProvider;

    public MediaHealth execute(){
        return youtubeMediaProvider.check();
    }
}


