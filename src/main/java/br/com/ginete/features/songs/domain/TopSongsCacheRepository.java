package br.com.ginete.features.songs.domain;

import java.util.List;

public interface TopSongsCacheRepository {
    void save(List<String> songIds);
    List<String> get();
    boolean tryLock();
    void unlock();
}
