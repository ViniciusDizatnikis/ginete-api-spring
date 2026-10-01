package br.com.rincaosonoro.apiRincaoSonoro.features.songs.application;

import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.CacheSongsRepository;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.Song;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetTopSongsUseCase {
    private final CacheSongsRepository cacheSongsRepository;
    private final SongRepository songRepository;

    public List<Song> execute() {
        var songIds = cacheSongsRepository.get();

        if (!songIds.isEmpty()) {
            return findSongs(songIds);
        }

        if (cacheSongsRepository.tryLock()) {

            try {
                songIds = cacheSongsRepository.get();

                if (songIds.isEmpty()) {

                    var songs = songRepository.findRandomSongs(10);

                    songIds = songs.stream()
                            .map(song -> song.getId().value())
                            .toList();

                    cacheSongsRepository.save(songIds);

                    return songs;
                }

            } finally {
                cacheSongsRepository.unlock();
            }
        }
        return findSongs(waitForTopSongs());
    }


    private List<Song> findSongs(List<String> songIds) {

        var songs = songRepository.findAllById(songIds);

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

            var songIds = cacheSongsRepository.get();

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
