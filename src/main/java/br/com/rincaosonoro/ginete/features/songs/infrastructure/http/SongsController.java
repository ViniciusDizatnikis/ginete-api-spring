package br.com.rincaosonoro.ginete.features.songs.infrastructure.http;

import br.com.rincaosonoro.ginete.features.songs.application.*;
import br.com.rincaosonoro.ginete.features.songs.infrastructure.http.response.SongResponse;
import br.com.rincaosonoro.ginete.features.songs.infrastructure.http.response.SongSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/songs")
@RequiredArgsConstructor
public class SongsController {
    private final GetAllSongsUseCase getAllSongsUseCase;
    private final FindSongByIdUseCase findSongByIdUseCase;
    private final GetNewSongsUseCase getNewSongsUseCase;
    private final GetFeaturedSongsUseCase getFeaturedSongsUseCase;
    private final GetTopSongsUseCase getTopSongsUseCase;

    @GetMapping
    public ResponseEntity<List<SongSummaryResponse>> getSongs(
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String search
    ) {
        var songs = getAllSongsUseCase.execute(limit, offset, search);
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
        var newSongs = getNewSongsUseCase.execute(limit, offset);
        var newSongsResponse = newSongs.stream().map(SongSummaryResponse::from).toList();
        return ResponseEntity.ok(newSongsResponse);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<SongSummaryResponse>> getFeaturedSongs(
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        var featuredSongs = getFeaturedSongsUseCase.execute(limit, offset);
        var featuredSongsResponse = featuredSongs.stream().map(SongSummaryResponse::from).toList();
        return ResponseEntity.ok(featuredSongsResponse);
    }

    @GetMapping("/top")
    public ResponseEntity<List<SongSummaryResponse>> getTopSongs() {
        var topSongs = getTopSongsUseCase.execute();
        var topSongsResponse = topSongs.stream().map(SongSummaryResponse::from).toList();
        return ResponseEntity.ok(topSongsResponse);
    }
}
