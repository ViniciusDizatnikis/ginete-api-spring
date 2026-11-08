package br.com.ginete.features.artists.infrastructure.persistence.repository;

import br.com.ginete.features.artists.domain.Artist;
import br.com.ginete.features.artists.domain.ArtistId;
import br.com.ginete.features.artists.domain.ArtistRepository;
import br.com.ginete.features.artists.infrastructure.persistence.entity.ArtistEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class MongoArtistRepository implements ArtistRepository {
    private final ArtistEntityRepository artistEntityRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Artist> findById(ArtistId id) {
        return artistEntityRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public List<Artist> findAll(int limit, int offset, String search) {
        Query query = new Query();

        if (search != null && !search.isBlank()) {
            query.addCriteria(
                    Criteria.where("name")
                            .regex(Pattern.quote(search), "i")
            );
        }

        query.skip(offset);
        query.limit(limit);

        query.with(Sort.by(Sort.Direction.ASC, "name"));

        return find(query);
    }

    @Override
    public List<Artist> findRecent(Instant since, int limit, int offset) {
        Query query = new Query();

        query.addCriteria(
                Criteria.where("createdAt")
                        .ne(null)
                        .gte(since)
        );

        query.skip(offset);
        query.limit(limit);

        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

        return find(query);
    }

    @Override
    public List<Artist> findFeatured(int limit, int offset) {
        Query query = new Query();

        query.addCriteria(
                Criteria.where("featured")
                        .is(true)
        );

        query.skip(offset);
        query.limit(limit);

        return find(query);
    }

    private List<Artist> find(Query query) {
        return mongoTemplate
                .find(query, ArtistEntity.class)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Artist toDomain(ArtistEntity entity) {
        var artist = new Artist();

        artist.setId(new ArtistId(entity.getId()));
        artist.setName(entity.getName());
        artist.setFeatured(entity.isFeatured());
        artist.setImage(toUri(entity.getImage()));
        artist.setBanner(toUri(entity.getBanner()));
        artist.setCreatedAt(entity.getCreatedAt());
        artist.setUpdatedAt(entity.getUpdatedAt());

        return artist;
    }

    private static URI toUri(String value) {
        return value != null ? URI.create(value) : null;
    }
}
