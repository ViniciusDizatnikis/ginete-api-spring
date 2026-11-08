package br.com.ginete.features.songs.application;

import br.com.ginete.features.songs.domain.Song;
import br.com.ginete.features.songs.domain.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListNewSongsUseCase {
    private static final Duration NEW_SONG_PERIOD = Duration.ofDays(7);

    private final SongRepository songRepository;

    public List<Song> execute(int limit, int offset) {
        Instant since = Instant.now().minus(NEW_SONG_PERIOD);

        return songRepository.findRecent(since, limit, offset);
    }
}
