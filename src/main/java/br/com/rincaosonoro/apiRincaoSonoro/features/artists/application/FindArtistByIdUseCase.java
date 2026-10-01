package br.com.rincaosonoro.apiRincaoSonoro.features.artists.application;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.Artist;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistId;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistRepository;
import org.springframework.stereotype.Service;

@Service
public class FindArtistByIdUseCase {
    private final ArtistRepository artistRepository;

    public FindArtistByIdUseCase(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist execute(String id) {
        ArtistId artistId = new ArtistId(id);

        return artistRepository.findById(artistId);
    }
}
