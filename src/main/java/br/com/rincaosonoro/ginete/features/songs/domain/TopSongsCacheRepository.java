package br.com.rincaosonoro.ginete.features.songs.domain;

import java.util.List;

public interface TopSongsCacheRepository {
    void save(List<String> songsIds);
    List<String> get();
    boolean tryLock();
    void unlock();
}
