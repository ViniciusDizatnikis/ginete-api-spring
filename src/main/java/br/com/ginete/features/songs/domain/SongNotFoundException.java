package br.com.ginete.features.songs.domain;

import br.com.ginete.shared.exception.NotFoundException;

public class SongNotFoundException extends NotFoundException {

    public SongNotFoundException(SongId id) {
        super("Song Not Found", "Song not found: " + id.value());
    }
}
