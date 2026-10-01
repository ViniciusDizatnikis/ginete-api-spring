package br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.http;

import br.com.rincaosonoro.apiRincaoSonoro.features.songs.application.*;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.http.response.SongResponse;
import br.com.rincaosonoro.apiRincaoSonoro.features.songs.infrastructure.http.response.TopSongResponse;
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
    public ResponseEntity<List<SongResponse>> findAllSongs(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String search
    ) {
        var songs = getAllSongsUseCase.execute(limit, offset, search);
        var songsResponse = songs.stream().map(SongResponse::from).toList();
        return ResponseEntity.ok(songsResponse);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SongResponse> finById(@PathVariable String id) {
        var song = findSongByIdUseCase.execute(id);
        return ResponseEntity.ok(SongResponse.from(song));
    }

    @GetMapping("/new")
    public ResponseEntity<List<SongResponse>> getNewSongs(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        var newSongs = getNewSongsUseCase.execute(limit, offset);
        var songsResponse = newSongs.stream().map(SongResponse::from).toList();
        return ResponseEntity.ok(songsResponse);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<SongResponse>> getFeatured(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        var featuredSongs = getFeaturedSongsUseCase.execute(limit, offset);
        var featuredSongsResponse = featuredSongs.stream().map(SongResponse::from).toList();
        return ResponseEntity.ok(featuredSongsResponse);
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopSongResponse>> getTopMusics() {
        var topSongs = getTopSongsUseCase.execute();
        var topSongsResponse = topSongs.stream().map(TopSongResponse::from).toList();
        return ResponseEntity.ok(topSongsResponse);
    }
}
