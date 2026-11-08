package br.com.ginete.features.songs.infrastructure.http;

import br.com.ginete.features.songs.application.*;
import br.com.ginete.features.songs.infrastructure.http.response.SongResponse;
import br.com.ginete.features.songs.infrastructure.http.response.SongSummaryResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.ginete.shared.config.openAPIConfig.TAG_SONGS;

@RestController
@RequestMapping("/v1/songs")
@Tag(name = TAG_SONGS)
@RequiredArgsConstructor
public class SongsController {
    private final ListSongsUseCase listSongsUseCase;
    private final FindSongByIdUseCase findSongByIdUseCase;
    private final ListNewSongsUseCase listNewSongsUseCase;
    private final ListFeaturedSongsUseCase listFeaturedSongsUseCase;
    private final ListTopSongsUseCase listTopSongsUseCase;

    @GetMapping
    public ResponseEntity<List<SongSummaryResponse>> getSongs(
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String search
    ) {
        var songs = listSongsUseCase.execute(limit, offset, search);
        var songsResponse = songs.stream().map(SongSummaryResponse::from).toList();
        return ResponseEntity.ok(songsResponse);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable String id) {
        var song = findSongByIdUseCase.execute(id);
        return ResponseEntity.ok(SongResponse.from(song));
    }

    @GetMapping("/new")
    public ResponseEntity<List<SongSummaryResponse>> getNewSongs(
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        var newSongs = listNewSongsUseCase.execute(limit, offset);
        var newSongsResponse = newSongs.stream().map(SongSummaryResponse::from).toList();
        return ResponseEntity.ok(newSongsResponse);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<SongSummaryResponse>> getFeaturedSongs(
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        var featuredSongs = listFeaturedSongsUseCase.execute(limit, offset);
        var featuredSongsResponse = featuredSongs.stream().map(SongSummaryResponse::from).toList();
        return ResponseEntity.ok(featuredSongsResponse);
    }

    @GetMapping("/top")
    public ResponseEntity<List<SongSummaryResponse>> getTopSongs() {
        var topSongs = listTopSongsUseCase.execute();
        var topSongsResponse = topSongs.stream().map(SongSummaryResponse::from).toList();
        return ResponseEntity.ok(topSongsResponse);
    }
}
