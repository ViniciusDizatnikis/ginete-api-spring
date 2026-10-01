package br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(SongId id) {
        super("Song not found: " + id.value());
    }
}
