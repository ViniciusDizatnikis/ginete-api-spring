package br.com.rincaosonoro.apiRincaoSonoro.features.artists.application;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.Artist;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetNewArtistsUseCase {
    private final ArtistRepository artistRepository;

    public List<Artist> execute(Integer limit, Integer offset){
        return artistRepository.findRecent(limit, offset);
    }
}
