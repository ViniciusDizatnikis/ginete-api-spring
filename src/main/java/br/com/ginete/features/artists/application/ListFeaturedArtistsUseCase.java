package br.com.ginete.features.artists.application;

import br.com.ginete.features.artists.domain.Artist;
import br.com.ginete.features.artists.domain.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListFeaturedArtistsUseCase {
    private final ArtistRepository artistRepository;

    public List<Artist> execute(int limit, int offset) {
        return artistRepository.findFeatured(limit, offset);
    }
}
