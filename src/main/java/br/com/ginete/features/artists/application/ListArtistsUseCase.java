package br.com.ginete.features.artists.application;

import br.com.ginete.features.artists.domain.Artist;
import br.com.ginete.features.artists.domain.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListArtistsUseCase {
    private final ArtistRepository artistRepository;

    public List<Artist> execute(int limit, int offset, String search) {
        return artistRepository.findAll(limit, offset, search);
    }
}
