package br.com.ginete.features.artists.application;

import br.com.ginete.features.artists.domain.Artist;
import br.com.ginete.features.artists.domain.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListNewArtistsUseCase {
    private static final Duration NEW_ARTIST_PERIOD = Duration.ofDays(7);

    private final ArtistRepository artistRepository;

    public List<Artist> execute(int limit, int offset) {
        Instant since = Instant.now().minus(NEW_ARTIST_PERIOD);

        return artistRepository.findRecent(since, limit, offset);
    }
}
