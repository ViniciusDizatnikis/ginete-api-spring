package br.com.rincaosonoro.ginete.features.artists.domain;

import java.util.List;

public interface ArtistRepository {
    Artist findById(ArtistId id);
    List<Artist> findAll(Integer limit, Integer offset, String search);
    List<Artist> findRecent(Integer limit, Integer offset);
    List<Artist> findFeatured(Integer limit, Integer offset);
}
