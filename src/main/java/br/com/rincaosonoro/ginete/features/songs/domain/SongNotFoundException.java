package br.com.rincaosonoro.ginete.features.songs.domain;

import br.com.rincaosonoro.ginete.shared.exception.NotFoundException;

public class SongNotFoundException extends NotFoundException {

    public SongNotFoundException(SongId id) {
        super("Song Not Found", "Song not found: " + id.value());
    }
}
