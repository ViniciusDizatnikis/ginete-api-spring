package br.com.ginete.features.artists.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ArtistRepository {
    Optional<Artist> findById(ArtistId id);
    List<Artist> findAll(int limit, int offset, String search);
    List<Artist> findRecent(Instant since, int limit, int offset);
    List<Artist> findFeatured(int limit, int offset);
    long count();
}
