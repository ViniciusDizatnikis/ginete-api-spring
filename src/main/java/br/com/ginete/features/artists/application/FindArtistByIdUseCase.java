package br.com.ginete.features.artists.application;

import br.com.ginete.features.artists.domain.Artist;
import br.com.ginete.features.artists.domain.ArtistId;
import br.com.ginete.features.artists.domain.ArtistNotFoundException;
import br.com.ginete.features.artists.domain.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindArtistByIdUseCase {
    private final ArtistRepository artistRepository;

    public Artist execute(String id) {
        var artistId = new ArtistId(id);

        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(artistId));
    }
}
