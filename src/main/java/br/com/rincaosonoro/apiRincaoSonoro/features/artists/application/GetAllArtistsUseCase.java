package br.com.rincaosonoro.apiRincaoSonoro.features.artists.application;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.Artist;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllArtistsUseCase {
    private final ArtistRepository artistRepository;

    public GetAllArtistsUseCase(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> execute(Integer limit, Integer offset, String search) {
        return artistRepository.findAll(limit, offset, search);
    }
}