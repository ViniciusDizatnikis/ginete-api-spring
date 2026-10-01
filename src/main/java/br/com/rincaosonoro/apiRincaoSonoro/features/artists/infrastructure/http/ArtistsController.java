package br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.http;

import br.com.rincaosonoro.apiRincaoSonoro.features.artists.application.GetAllArtistsUseCase;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.application.FindArtistByIdUseCase;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.application.GetFeaturedArtistsUseCase;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.application.GetNewArtistsUseCase;
import br.com.rincaosonoro.apiRincaoSonoro.features.artists.infrastructure.http.response.ArtistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/artists")
@RequiredArgsConstructor
public class ArtistsController {

    private final GetAllArtistsUseCase getAllArtistsUseCase;
    private final FindArtistByIdUseCase findArtistByIdUseCase;
    private final GetNewArtistsUseCase getNewArtistsUseCase;
    private final GetFeaturedArtistsUseCase getFeaturedArtistsUseCase;

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getArtists(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String search
    ) {
        var artists = getAllArtistsUseCase.execute(limit, offset, search);
        var artistsResponse = artists.stream().map(ArtistResponse::from).toList();

        return ResponseEntity.ok(artistsResponse);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable String id) {
        var artist = findArtistByIdUseCase.execute(id);
        return ResponseEntity.ok(ArtistResponse.from(artist));
    }

    @GetMapping("/new")
    public ResponseEntity<List<ArtistResponse>> getNewArtists(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        var newArtists = getNewArtistsUseCase.execute(limit, offset);
        var newArtistsResponse = newArtists.stream().map(ArtistResponse::from).toList();
        return ResponseEntity.ok(newArtistsResponse);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ArtistResponse>> getFeatured(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        var featuredArtists = getFeaturedArtistsUseCase.execute(limit, offset);
        var featuredArtistsResponse = featuredArtists.stream().map(ArtistResponse::from).toList();
        return ResponseEntity.ok(featuredArtistsResponse);
    }
}
