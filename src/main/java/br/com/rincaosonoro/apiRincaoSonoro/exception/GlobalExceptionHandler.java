package br.com.rincaosonoro.apiRincaoSonoro.exception;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.domain.ArtistNotFoundException;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.domain.SongNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<String> handleArtistNotFound(ArtistNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<String> handleSongNotFound(SongNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception) {

        log.error("Erro interno no servidor", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro interno no servidor.");
    }
}
