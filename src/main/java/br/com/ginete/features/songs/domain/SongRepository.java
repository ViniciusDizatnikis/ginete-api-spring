package br.com.ginete.features.songs.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SongRepository {
    Optional<Song> findById(SongId id);
    List<Song> findAllById(List<SongId> ids);
    List<Song> findAll(int limit, int offset, String search);
    List<Song> findRecent(Instant since, int limit, int offset);
    List<Song> findFeatured(int limit, int offset);
    List<Song> findRandomSongs(int limit);
}
