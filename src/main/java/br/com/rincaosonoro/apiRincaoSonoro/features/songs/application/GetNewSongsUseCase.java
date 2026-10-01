package br.com.rincaosonoro.apiRincaoSonoro.features.songs.application;

import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.Song;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetNewSongsUseCase {
    private final SongRepository songRepository;

    public List<Song> execute(Integer limit, Integer offset) {
        return songRepository.findRecent(limit, offset);
    }
}
