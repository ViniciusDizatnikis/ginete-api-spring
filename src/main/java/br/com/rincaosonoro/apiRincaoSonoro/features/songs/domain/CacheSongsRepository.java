package br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain;

import java.util.List;

public interface CacheSongsRepository {
    void save(List<String> songsIds);
    List<String> get();
    boolean tryLock();
    void unlock();
}
