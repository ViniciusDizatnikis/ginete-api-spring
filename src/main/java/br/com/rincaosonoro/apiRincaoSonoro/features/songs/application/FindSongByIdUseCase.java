package br.com.rincaosonoro.apiRincaoSonoro.features.songs.application;

import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.Song;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongId;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindSongByIdUseCase {
    private final SongRepository songRepository;

    public Song execute(String id) {
        var songId = new SongId(id);

        return songRepository.findById(songId);
    }
}
