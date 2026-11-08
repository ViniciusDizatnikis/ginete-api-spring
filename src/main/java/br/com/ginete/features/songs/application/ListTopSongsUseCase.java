package br.com.ginete.features.songs.application;

import br.com.ginete.features.songs.domain.Song;
import br.com.ginete.features.songs.domain.SongId;
import br.com.ginete.features.songs.domain.SongRepository;
import br.com.ginete.features.songs.domain.TopSongsCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListTopSongsUseCase {
    private static final int TOP_SONGS_LIMIT = 10;

    private final TopSongsCacheRepository topSongsCacheRepository;
    private final SongRepository songRepository;

    public List<Song> execute() {
        var songIds = topSongsCacheRepository.get();

        if (!songIds.isEmpty()) {
            return findSongs(songIds);
        }

        if (topSongsCacheRepository.tryLock()) {

            try {
                songIds = topSongsCacheRepository.get();

                if (songIds.isEmpty()) {

                    var songs = songRepository.findRandomSongs(TOP_SONGS_LIMIT);

                    songIds = songs.stream()
                            .map(song -> song.getId().value())
                            .toList();

                    topSongsCacheRepository.save(songIds);

                    return songs;
                }

            } finally {
                topSongsCacheRepository.unlock();
            }
        }
        return findSongs(waitForTopSongs());
    }


    private List<Song> findSongs(List<String> songIds) {

        var songs = songRepository.findAllById(
                songIds.stream().map(SongId::new).toList()
        );

        return orderByIds(songs, songIds);
    }

    private List<Song> orderByIds(
            List<Song> songs,
            List<String> songIds
    ) {

        var songsById = songs.stream()
                .collect(Collectors.toMap(
                        song -> song.getId().value(),
                        Function.identity()
                ));

        return songIds.stream()
                .map(songsById::get)
                .filter(Objects::nonNull)
                .toList();
    }

    private List<String> waitForTopSongs() {

        for (int i = 0; i < 10; i++) {

            var songIds = topSongsCacheRepository.get();

            if (!songIds.isEmpty()) {
                return songIds;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return List.of();
            }
        }

        return List.of();
    }
}
