package br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain;

import java.util.List;

public interface SongRepository {
    Song findById(SongId id);
    List<Song> findAllById(List<String> ids);
    List<Song> findAll(Integer limit, Integer offset, String search);
    List<Song> findRecent(Integer limit, Integer offset);
    List<Song> findFeatured(Integer limit, Integer offset);
    List<Song> findRandomSongs(int limit);
}
