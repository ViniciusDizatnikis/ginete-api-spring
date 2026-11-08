package br.com.ginete.features.songs.application;

import br.com.ginete.features.songs.domain.Song;
import br.com.ginete.features.songs.domain.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListSongsUseCase {
    private final SongRepository songRepository;

    public List<Song> execute(int limit, int offset, String search) {
        return songRepository.findAll(limit, offset, search);
    }
}
