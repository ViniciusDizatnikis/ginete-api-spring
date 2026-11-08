package br.com.ginete.features.artists.infrastructure.http;

import br.com.ginete.features.artists.application.FindArtistByIdUseCase;
import br.com.ginete.features.artists.application.ListArtistsUseCase;
import br.com.ginete.features.artists.application.ListFeaturedArtistsUseCase;
import br.com.ginete.features.artists.application.ListNewArtistsUseCase;
import br.com.ginete.features.artists.infrastructure.http.response.ArtistResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.ginete.shared.config.openAPIConfig.TAG_ARTISTS;

@RestController
@RequestMapping("/v1/artists")
@Tag(name = TAG_ARTISTS)
@RequiredArgsConstructor
public class ArtistsController {

    private final ListArtistsUseCase listArtistsUseCase;
    private final FindArtistByIdUseCase findArtistByIdUseCase;
    private final ListNewArtistsUseCase listNewArtistsUseCase;
    private final ListFeaturedArtistsUseCase listFeaturedArtistsUseCase;

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getArtists(
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String search
    ) {
        var artists = listArtistsUseCase.execute(limit, offset, search);
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
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        var newArtists = listNewArtistsUseCase.execute(limit, offset);
        var newArtistsResponse = newArtists.stream().map(ArtistResponse::from).toList();
        return ResponseEntity.ok(newArtistsResponse);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ArtistResponse>> getFeaturedArtists(
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        var featuredArtists = listFeaturedArtistsUseCase.execute(limit, offset);
        var featuredArtistsResponse = featuredArtists.stream().map(ArtistResponse::from).toList();
        return ResponseEntity.ok(featuredArtistsResponse);
    }
}
